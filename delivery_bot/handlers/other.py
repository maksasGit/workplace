from aiogram import Bot, Dispatcher, executor, types
from aiogram.types import ReplyKeyboardRemove,  ReplyKeyboardMarkup, KeyboardButton,  InlineKeyboardMarkup, InlineKeyboardButton
from aiogram.contrib.fsm_storage.memmory import MemmoryStorage


#main reactions
@dp.message_handler()
async def subscribe(message: types.Message):

	match message.text:
		case "Заказы":
			await message.answer("Пока не умею") 
		case "Найти заказ":
			await message.answer("Пока не умею") 
		case "Моя статистика":
			await message.answer("Пока не умею") 
		case "Что я заказал?": 
				for i in db.cursor.execute("SELECT * FROM orders WHERE `user_id` = ?", (message.from_user.id,)):
					await message.answer(i[2])	
		case "Добавить заказ":
			await message.answer("Опишите заказ")
			#wait for user order
			db.add_user_order(message.from_user.id , message.text)
		case _:
			await message.answer("Не пон...") 

