package com.example.ddschedule;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.ddschedule.db.AppDataBase;
import com.example.ddschedule.db.GroupDao;
import com.example.ddschedule.db.ScheduleDao;
import com.example.ddschedule.model.GroupModel;
import com.example.ddschedule.model.ScheduleModel;

import java.util.List;

public class DataRepository {

    private ScheduleDao mScheduleDao;
    private LiveData<List<ScheduleModel>> mAllSchedules;

    private GroupDao mGroupDao;
    private LiveData<List<GroupModel>> mAllGroups;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    DataRepository(Application application) {
        AppDataBase db = AppDataBase.getDatabase(application);

        mScheduleDao = db.scheduleDao();
        mAllSchedules = mScheduleDao.getSchedules();

        mGroupDao = db.groupDao();
        mAllGroups = mGroupDao.getGroups();
    }

    LiveData<List<ScheduleModel>> getAllSchedules() {
        return mAllSchedules;
    }
    LiveData<List<GroupModel>> getAllGroups() {
        return mAllGroups;
    }

    void insertSchedules(List<ScheduleModel> schedules) {
        AppDataBase.databaseWriteExecutor.execute(() -> {
            mScheduleDao.insertAll(schedules);
        });
    }

    void insertGroups(List<GroupModel> groups) {
        AppDataBase.databaseWriteExecutor.execute(() -> {
            mGroupDao.insertAll(groups);
        });
    }
}
