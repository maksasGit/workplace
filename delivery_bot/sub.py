import logging
import config


from aiogram.types import ReplyKeyboardRemove,  ReplyKeyboardMarkup, KeyboardButton,  InlineKeyboardMarkup, InlineKeyboardButton
from aiogram.contrib.fsm_storage.memory import MemoryStorage
from aiogram import Bot, Dispatcher, executor, types
from aiogram.dispatcher import FSMContext
from aiogram.dispatcher.filters import Text
from aiogram.dispatcher.filters.state import State, StatesGroup
from db import usersBD
from FSM import FSMAdmin



storage=MemoryStorage()


logging.basicConfig(level=logging.INFO, format = "%(asctime)s - %(levelname)s - %(funcName)s: %(lineno)d - %(message)s")
db = usersBD('bd.db')
API_TOKEN = '5150043929:AAFSzAQAsDPfWdrIeTIPotpil-u6TWeTdiA'
bot = Bot(token = API_TOKEN)
dp = Dispatcher(bot , storage=storage)



################     User Welcome     #######################################


@dp.message_handler(commands=['start'])
async def subscribe(message: types.Message):
	if(not db.subscriber_exists(message.from_user.id)):
		# если юзера нет в базе, добавляем его
		db.add_subscriber(message.from_user.id)
		await message.answer("Hello!!!")
	else:
		# если он уже есть, то просто обновляем ему статус подписки
		db.update_subscription(message.from_user.id, True)
		await message.answer("Hello, again!!!")
	

	button_add = KeyboardButton('Добавить')
	button_execute = KeyboardButton('Выполнить')
	button_adds = KeyboardButton('Добавленные')
	button_wait = KeyboardButton('Ждут')
	button_stats = KeyboardButton('Статистика')

	field = ReplyKeyboardMarkup()

	field.row(button_add, button_execute)
	field.row(button_adds, button_wait)
	field.add(button_stats)
	await message.answer("Добрый день, все ваши данные были успешно спизжены, спасибо)))", reply_markup=field)


##################      Add order      ##############################################3


@dp.message_handler(state="*", text=['отмена'])
@dp.message_handler(Text(equals='отмена', ignore_case=True) , state="*")
async def cancel(message: types.Message, state: FSMContext):
	current_state = await state.get_state()
	if current_state is None:
		return
	await state.finish()
	await message.reply("OK")


@dp.message_handler(text=['Добавить'], state=None)
async def order_start(message : types.Message):
		await FSMAdmin.next()
		await message.reply('Оформляем заказ....\nКуда доставить?')


@dp.message_handler(state=FSMAdmin.place)
async def load_place(message: types.Message, state=FSMContext):
	async with state.proxy() as data:
		data['place'] = message.text
	await FSMAdmin.next()
	await message.reply("Опиши заказ...")

@dp.message_handler(state=FSMAdmin.description)
async def load_description(message: types.Message, state=FSMContext):
	async with state.proxy() as data:
		data['description'] = message.text
	await FSMAdmin.next()
	await message.reply("Назначь вознаграждение...")

@dp.message_handler(state=FSMAdmin.reward)
async def load_reward(message: types.Message, state=FSMContext):
	async with state.proxy() as data:
		data['reward'] = message.text

	db.add_user_order(message.from_user.id , tuple(data.values())[0] , tuple(data.values())[1] , tuple(data.values())[2])
	await state.finish()
	await message.reply("Заказ успешно добавлен...\nМесто: " + tuple(data.values())[0] + "\nОписание заказа: " + tuple(data.values())[1] + "\nВознаграждение: " + tuple(data.values())[2]	)


#######################    Other requaries     #######################################

@dp.message_handler()
async def subscribe(message: types.Message):

	match message.text:
		case "Выполнить":
			for i in db.cursor.execute("SELECT * FROM orders WHERE `status` == ? AND `user_id` != ?", (0, message.from_user.id,)):
				await message.answer("Заказ номер: " + str(i[0]) + "\nМесто: " + i[2] + "\nОписание заказа: " + i[3] + "\nВознаграждение: " + i[4], reply_markup=InlineKeyboardMarkup().add(InlineKeyboardButton('Принять' , callback_data=f'button_add {i[0]}' )))
		case "Ждут":
			for i in db.cursor.execute("SELECT * FROM orders WHERE `deliver_id` = ?", (message.from_user.id,)):
				await message.answer("Заказ номер: " + str(i[0]) + "\nМесто: " + i[2] + "\nОписание заказа: " + i[3] + "\nВознаграждение: " + i[4], reply_markup=InlineKeyboardMarkup().add(InlineKeyboardButton('Отменить' , callback_data=f'button_cancel {i[0]}') , InlineKeyboardButton('Готово' , callback_data=f'button_ready {i[0]}')))
		case "Статистика":
			await message.answer("Пока не умею") 
		case "Добавленные": 
			for i in db.cursor.execute("SELECT * FROM orders WHERE `user_id` = ? AND `status` <> ?", (message.from_user.id,2,)):
				await message.answer("Заказ номер: " + str(i[0]) + "\nМесто: " + i[2] + "\nОписание заказа: " + i[3] + "\nВознаграждение: " + i[4], reply_markup=InlineKeyboardMarkup().add(InlineKeyboardButton('Удалить' , callback_data=f'button_delete {i[0]}' )))



#########################    Handling Inline Button      ################################################################



															###### Change order



@dp.callback_query_handler(Text(startswith='button_change'))
async def button_change(callback: types.CallbackQuery):
	await callback.message.reply("Пока не умею")
	await callback.answer()



															##### Delete order



@dp.callback_query_handler(Text(startswith='button_delete'))
async def button_delete(callback: types.CallbackQuery):
	order_id = callback.data.replace('button_delete ' , '')
	result = db.cursor.execute("SELECT * FROM orders WHERE `id` = ?", (order_id,)).fetchall()
	if bool(len(result)) == 0:
		await callback.message.reply("Заказ уже удалён")
	else:
		i = db.cursor.execute("SELECT `status` FROM orders WHERE `id` = ?", (order_id,)).fetchall()[0][0]
		if i == 0:
			db.cursor.execute("DELETE FROM orders WHERE `id` = ?", (order_id,))
			await callback.message.reply("Заказ удалён")
		else:
			await callback.message.reply("Заказ уже выполняется")
	await callback.answer()



														######### Order ready



@dp.callback_query_handler(Text(startswith='button_ready'))
async def button_add(callback: types.CallbackQuery):
	order_id = callback.data.replace('button_ready ' , '')
	i = db.cursor.execute("SELECT `status` FROM orders WHERE `id` = ?", (order_id,)).fetchall()[0][0]
	if (i != 1):
		await callback.message.reply("Заказ уже выполнен или отменён")
	else:
		db.cursor.execute("UPDATE orders SET `status` = ? WHERE `id` = ?", (2, order_id))
		db.cursor.execute("UPDATE orders SET `deliver_id` = ? WHERE `id` = ?", (-1, order_id))
		user_id = db.cursor.execute("SELECT `user_id` FROM orders WHERE `id` = ?", (order_id,)).fetchall()[0][0]
		await bot.send_message(user_id , f"Ваш заказ("+str(order_id)+") готов...")                                                                           ## add : rate deliver
	await callback.answer()






        											##### Take order



@dp.callback_query_handler(Text(startswith='button_add'))
async def button_add(callback: types.CallbackQuery):
	order_id = callback.data.replace('button_add ' , '')
	result = db.cursor.execute("SELECT * FROM orders WHERE `id` = ?", (order_id,)).fetchall()
	if bool(len(result)) == 0:
		await callback.message.reply("Заказ уже удалён")
	else:
		i = db.cursor.execute("SELECT `status` FROM orders WHERE `id` = ?", (order_id,)).fetchall()[0][0]
		if i != 0:
			await callback.message.reply("Статус заказа уже измнеён(")
		else:
			user_id = db.cursor.execute("SELECT `user_id` FROM orders WHERE `id` = ?", (order_id,)).fetchall()[0][0]
			db.cursor.execute("UPDATE orders SET `status` = ? WHERE `id` = ?", (1, order_id,))
			db.cursor.execute("UPDATE orders SET `deliver_id` = ? WHERE `id` = ?", (callback.from_user.id, order_id,))
			await bot.send_message(user_id , f"Ваш заказ("+str(order_id)+") доставляется...")                                                                    ### add: go to this order
			await callback.message.reply("Принято")
	await callback.answer()

#### report order

@dp.callback_query_handler(Text(startswith='button_report'))
async def button_report(callback: types.CallbackQuery):
	#await callback.answer("Репорт?" , show_alert=True)
	await callback.message.reply("Пока не могу")



                                                       ### cancel order



@dp.callback_query_handler(Text(startswith='button_cancel'))
async def button_report(callback: types.CallbackQuery):
	order_id = callback.data.replace('button_cancel ' , '')
	i = db.cursor.execute("SELECT `status` FROM orders WHERE `id` = ?", (order_id,)).fetchall()[0][0]
	if (i != 1):
		await callback.message.reply("Заказ уже выполнен или отменён")
	else:
		db.cursor.execute("UPDATE orders SET `status` = ? WHERE `id` = ?", (0, order_id))
		db.cursor.execute("UPDATE orders SET `deliver_id` = ? WHERE `id` = ?", (-1, order_id))
		user_id = db.cursor.execute("SELECT `user_id` FROM orders WHERE `id` = ?", (order_id,)).fetchall()[0][0]
		await bot.send_message(user_id , f"Ваш заказ("+str(order_id)+") отменён...")                                                                           ## add : go to this order + rate deliver
	await callback.answer()



################################################################################


#non-stop
if __name__ == '__main__':
    executor.start_polling(dp, skip_updates=True)