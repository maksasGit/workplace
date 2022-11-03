import sqlite3


class usersBD:

    def __init__(self, database):
        """Подключаемся к БД и сохраняем курсор соединения"""
        self.connection = sqlite3.connect(database)
        self.cursor = self.connection.cursor()

    def get_subscriptions(self, status = True):
        """Получаем всех активных подписчиков бота"""
        with self.connection:
            return self.cursor.execute("SELECT * FROM all_users WHERE `status` = ?", (status,)).fetchall()

    def subscriber_exists(self, user_id):
        """Проверяем, есть ли уже юзер в базе"""
        with self.connection:
            result = self.cursor.execute('SELECT * FROM all_users WHERE `user_id` = ?', (user_id,)).fetchall()
            return bool(len(result))

    def add_subscriber(self, user_id, status = True):
        """Добавляем нового подписчика"""
        with self.connection:
            return self.cursor.execute("INSERT INTO all_users (`user_id`, `status`) VALUES(?,?)", (user_id,status))

    def update_subscription(self, user_id, status):
        """Обновляем статус подписки пользователя"""
        with self.connection:
            return self.cursor.execute("UPDATE all_users SET `status` = ? WHERE `user_id` = ?", (status, user_id))

    def get_users_orders(self, user_id):
    	with self.connection:
    		return self.cursor.execute("SELECT * FROM orders WHERE `user_id` = ?", (user_id,)).fetchall()

    def add_user_order(self, user_id, place , description, reward):
    	with self.connection:
    		return self.cursor.execute("INSERT INTO orders (`user_id`,`place`, `description`, `reward`, `deliver_id`) VALUES (?,?,?,?,?)", (user_id, place, description, reward, -1))

    def close(self):
        """Закрываем соединение с БД"""
        self.connection.close()


