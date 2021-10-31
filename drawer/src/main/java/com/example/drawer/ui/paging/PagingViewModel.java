package com.example.drawer.ui.paging;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.*;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CoroutineScope;

/**
 * @program: MyDemo
 * @author: zhuhe
 * @create: 2021-10-31 17:38
 **/
public class PagingViewModel extends ViewModel {

    PagingConfig pagingConfig = new PagingConfig(10, 10, false, 10);//初始化配置,可以定义最大加载的数据量

    StudentDao studentDao;

    public LiveData<PagingData<Student>> getPaging() {
        CoroutineScope viewmodelScope = ViewModelKt.getViewModelScope(this);
        Pager<Integer, Student> pager = new Pager<>(pagingConfig, new Function0<PagingSource<Integer, Student>>() {
            @Override
            public PagingSource<Integer, Student> invoke() {
                return studentDao.getAllStudents();
            }
        });
        return PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager), viewmodelScope);
    }

    public void setPagingSource(StudentDao studentDao) {
        this.studentDao = studentDao;
    }
}