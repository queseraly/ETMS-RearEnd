package com.group8.service.impl;

import com.group8.dao.AbilityModelDao;
import com.group8.dao.ItemDao;
import com.group8.dao.OutlineDao;
import com.group8.dto.AbilityModelSubject;
import com.group8.dto.EtmsItemAbilityOutline;
import com.group8.entity.*;
import com.group8.service.ItemService;
import com.group8.utils.TidyAbilityModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;




@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired(required = false)
    ItemDao itemDao;
    @Autowired(required = false)
    AbilityModelDao abilityModelDao;
    @Autowired(required = false)
    OutlineDao outlineDao;

    @Override
    public EtmsItem findById(int id) {
        return itemDao.findById(id);
    }

    @Override
    public int update(EtmsItem etmsItem) {
        return itemDao.update(etmsItem);
    }

    @Override
    public List<EtmsCatalog> findCatalogByTid(int id) {
        List<EtmsCatalog> catalogs = itemDao.findCatalogByTid(id);
        return catalogs;
    }

    @Override
    public List<EtmsClassFile> findClassFileByCid(int id,String catalogName) {
        List<EtmsClassFile> classFiles = itemDao.findClassFileByCid(id,catalogName);
        return classFiles;
    }

    @Override
    public String findSchedule(int uid, int tid) {
        String schedule = itemDao.findSchedule(uid, tid);
        return schedule;
    }

    @Override
    public String findClassNum(int tid) {
        int allClass = itemDao.findClassNum(tid);
        String classNum = String.valueOf(allClass);
        return classNum;
    }

    /*
    添加培训项目 从DTO从取得3个对象
     */
        @Override
        public int addItem (EtmsItemAbilityOutline iao){

            int i2 = 0;
            int i3 = 0;

            //添加培训项目
            EtmsItem etmsItem = iao.getEtmsItem();
            LocalDateTime now = LocalDateTime.now();
            etmsItem.setCreatedTime(now);
            int i1 = itemDao.addOne(etmsItem);
            long itemId = etmsItem.getItemId();

            //添加大纲集合
            List<EtmsOutline> etmsOutlines = iao.getEtmsOutlines();
            for (int i = 0; i < etmsOutlines.size(); i++) {
                etmsOutlines.get(i).setCatalog("目录" +1+ i);
                etmsOutlines.get(i).setItemId(itemId);
            }
            i2 = outlineDao.addOne(iao.getEtmsOutlines());

            //添加能力模型
            List<AbilityModelSubject> list = iao.getAmSubjectLists();
            for (AbilityModelSubject ability:list
                 ) {
                ability.setSubjectId(itemId);
            }
            list.get(0).setSubject("item");
            i3 = abilityModelDao.addOne(list);
            //如果其中一项不大于0 则添加失败
            if (i1 > 0 && i2 > 0 && i3 > 0) {
                return 1;
            } else {
                return 0;
            }
        }
        @Override
        public List<EtmsItem> findItem (EtmsItem etmsItem){
            List<EtmsItem> list = itemDao.findItem(etmsItem);
            System.out.println("获取的集合:"+list);
            return list;
        }

    @Override
    public int findMyItemSum(int uid) {
        return itemDao.findMyItemSum(uid);
    }

    @Override
    public List<EtmsItem> findAllItem(int uid) {
        return itemDao.findAllItem(uid);
    }

    @Override
    public int deleteOne(int itemId) {
        return itemDao.deleteOne(itemId);
    }

    @Override
    public List<EtmsAbilityModel> findAMById(int id) {
        AbilityModelSubject modelSubject = new AbilityModelSubject();
        modelSubject.setSubjectId(id);
        modelSubject.setSubject("item");
        List<EtmsAbilityModel> abilityModelList = abilityModelDao.findAll(modelSubject);
        return TidyAbilityModel.tidy(abilityModelList);
    }

    @Override
    public int updateAbilityModel(AbilityModelSubject abilityModelSubject) {
        return abilityModelDao.updateAbilityModel(abilityModelSubject);
    }
}

