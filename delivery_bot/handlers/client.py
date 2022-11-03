from aiogram import Bot, Dispatcher, executor, types
from aiogram.types import ReplyKeyboardRemove,  ReplyKeyboardMarkup, KeyboardButton,  InlineKeyboardMarkup, InlineKeyboardButton
from aiogram.contrib.fsm_storage.memmory import MemmoryStorage



#start
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
	

	button1 = KeyboardButton('Заказы')
	button2 = KeyboardButton('Добавить заказ')
	button3 = KeyboardButton('Найти заказ')
	button4 = KeyboardButton('Моя статистика')
	button5 = KeyboardButton('Что я заказал?')

	field = ReplyKeyboardMarkup()

	field.row(button1, button2)
	field.row(button3, button4)
	field.add(button5)
	await message.answer("Добрый день, все ваши данные были спизжены, спасибо)))", reply_markup=field)
