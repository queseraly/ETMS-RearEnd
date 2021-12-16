package com.group8.dao;

import com.group8.entity.EtmsUser;

import java.util.List;

public interface UserDao {
    List<EtmsUser> findByDeptId(int deptId);

    List<EtmsUser> findAllUser();

    EtmsUser findUserById(@Param("id")int id);

    boolean updateUser(EtmsUser etmsUser);

    boolean updatePassword(@Param("id") int id,@Param("newPassword") String newPassword);

    List<EtmsCourse> findCoursesByid(@Param("id")int userid);

    List<EtmsItem> findItemById(@Param("id")int userid);

    List<EtmsUserAm> findAbilityById(@Param("id")int userid);
}
