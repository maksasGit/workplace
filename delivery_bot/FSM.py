from aiogram.dispatcher import FSMContext
from aiogram.dispatcher.filters.state import State, StatesGroup


class FSMAdmin(StatesGroup):
	place = State()
	description = State()
	reward = State()