package ru.luzhnykh.socialnet.dao;

/**
 * Dao сущности Друг
 */
public interface FriendDao {

    /**
     * Добавить друга
     *
     * @param userId   ИД пользователя, которому добавляем друга
     * @param friendId ИД друга
     */
    void add(String userId, String friendId);

    /**
     * Отфрендить друга
     *
     * @param userId   ИД пользователя, у которого отфрендиваем
     * @param friendId ИД друга
     */
    void delete(String userId, String friendId);
}
