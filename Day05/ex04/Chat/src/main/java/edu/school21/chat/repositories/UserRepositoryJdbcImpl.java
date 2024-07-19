package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryJdbcImpl implements UsersRepository {
    private final String sqlSqript = "WITH user_created_rooms(user_id, room_id, name) \n" +
            "\tAS (\n" +
            "\t\tSELECT users.id, chatrooms.id, name\n" +
            "\t\tFROM chatrooms \n" +
            "\t\tRIGHT JOIN users ON users.id=chatrooms.owner\n" +
            "\t\tORDER BY users.id\n" +
            "\t),\n" +
            "\tuser_socializes_in_chatrooms(user_id, room_id, name) \n" +
            "\tAS (\n" +
            "\t\tSELECT users.id, chatrooms.id , name\n" +
            "\t\tFROM chatrooms \n" +
            "\t\tJOIN messages ON messages.room = chatrooms.id\n" +
            "\t\tRIGHT JOIN users ON messages.author = users.id\n" +
            "\t\tORDER BY users.id\n" +
            "\t)\n" +
            "\n" +
            "SELECT user_id,\n" +
            "       ARRAY_AGG(DISTINCT created_rooms) AS created_rooms,\n" +
            "       ARRAY_AGG(DISTINCT socializing_rooms) AS socializing_rooms\n" +
            "FROM (\n" +
            "    SELECT user_id, room_id AS created_rooms, NULL AS socializing_rooms\n" +
            "    FROM user_created_rooms\n" +
            "    UNION ALL\n" +
            "    SELECT user_id, NULL AS created_rooms, room_id AS socializing_rooms\n" +
            "    FROM user_socializes_in_chatrooms\n" +
            ") AS combined_tables\n" +
            "GROUP BY user_id;";
    private final DataSource dataSource;

    public UserRepositoryJdbcImpl(DataSource dataSource) {
            this.dataSource = dataSource;
        }
    public List<User> findAll(int page, int size) {
        List<User> users = new ArrayList<>();
        MessagesRepositoryJdbcImpl mr = new MessagesRepositoryJdbcImpl(dataSource);
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sqlSqript)) {
            ResultSet rs = ps.executeQuery();
            int currentUser = 0;
            int startUser = page * size;
            int lastUser = startUser + size;
            while (rs.next() && currentUser < lastUser) {
                if (currentUser++ < startUser) {
                    continue;
                }
                List<Chatroom> createdRooms = new ArrayList<>();
                List<Chatroom> socializingRooms = new ArrayList<>();
                long userId = rs.getLong("user_id");
                Array createdRoomsIdArray = rs.getArray("created_rooms");
                Array socializingRoomsIdArray = rs.getArray("socializing_rooms");

                if (createdRoomsIdArray != null) {
                    Long[] createdRoomsData = (Long[]) createdRoomsIdArray.getArray();
                    for (int i = 0; i < createdRoomsData.length - 1; i++) {
                        Chatroom room = mr.findChatroomById(createdRoomsData[i]);
                        createdRooms.add(room);
                    }
                }

                if (socializingRoomsIdArray != null) {
                    Long[] socializingRoomsData = (Long[]) socializingRoomsIdArray.getArray();
                    for (int i = 0; i < socializingRoomsData.length - 1; i++) {
                        Chatroom room = mr.findChatroomById(socializingRoomsData[i]);
                        socializingRooms.add(room);
                    }
                }

                User user = mr.findUserById(userId);
                user.setCreatedRooms(createdRooms);
                user.setSocializingChatrooms(socializingRooms);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }


}
