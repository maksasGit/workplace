import logging
import config

from aiogram import Bot, Dispatcher, executor, types
from db import usersBD


#loggs
logging.basicConfig(level=logging.INFO)


#Data Base 
db = usersBD('bd.db')

API_TOKEN = '5150043929:AAFSzAQAsDPfWdrIeTIPotpil-u6TWeTdiA'

bot = Bot(token = API_TOKEN)
dp = Dispatcher(bot)