package com.cbrc.dashboard.service.impl;

import com.cbrc.dashboard.dao.dto.*;
import com.cbrc.dashboard.dao.mapper.IWorklogMapper;
import com.cbrc.dashboard.service.IWorklogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorklogServiceImpl implements IWorklogService {
    @Autowired
    private IWorklogMapper worklogMapper;

    @Override
    public List<TimelineDto> getWorklogList() {
        List<WorklogDto> list = worklogMapper.getWorklogList();
        List<TimelineDto> rv = new ArrayList<>();
        String year = "";
        String month="";
        String period="";
        for (WorklogDto wl:list) {
            if(!year.equals(wl.getYear())){
                TimelineDto tl = new TimelineDto();
                tl.setYear(wl.getYear());
                tl.setYearlog(new ArrayList<>());
                rv.add(tl);
                YearlogDto yl = new YearlogDto();
                yl.setMonth(wl.getMonth());
                yl.setMonthwork(new ArrayList<>());
                tl.getYearlog().add(yl);
                MonthWorkDto mw = new MonthWorkDto();
                mw.setWorkdate(wl.getPeriod());
                mw.setWorkcnt(new ArrayList<>());
                yl.getMonthwork().add(mw);
                WorkContentDto wct = new WorkContentDto();
                wct.setDesc(wl.getContents());
                wct.setTitle(wl.getTitle());
                mw.getWorkcnt().add(wct);
                year = wl.getYear();
                month = wl.getMonth();
                period = wl.getPeriod();
            }else {
                //年份未变
                if(!month.equals(wl.getMonth())){
                    YearlogDto yl = new YearlogDto();
                    yl.setMonth(wl.getMonth());
                    yl.setMonthwork(new ArrayList<>());
                    List<YearlogDto> yll = getYearlogListByYear(wl.getYear(),rv);
                    yll.add(yl);
                    MonthWorkDto mw = new MonthWorkDto();
                    mw.setWorkdate(wl.getPeriod());
                    mw.setWorkcnt(new ArrayList<>());
                    yl.getMonthwork().add(mw);
                    WorkContentDto wct = new WorkContentDto();
                    wct.setDesc(wl.getContents());
                    wct.setTitle(wl.getTitle());
                    mw.getWorkcnt().add(wct);
                    month = wl.getMonth();
                    period = wl.getPeriod();
                }else {
                    if(!period.equals(wl.getPeriod())){
                        MonthWorkDto mw = new MonthWorkDto();
                        mw.setWorkdate(wl.getPeriod());
                        mw.setWorkcnt(new ArrayList<>());
                        List<MonthWorkDto> mwl = getMonthWorkListByYearAndMonth(wl.getYear(),wl.getMonth(),rv);
                        mwl.add(mw);
                        WorkContentDto wct = new WorkContentDto();
                        wct.setDesc(wl.getContents());
                        wct.setTitle(wl.getTitle());
                        mw.getWorkcnt().add(wct);
                        period = wl.getPeriod();
                    }else {
                        WorkContentDto wct = new WorkContentDto();
                        wct.setDesc(wl.getContents());
                        wct.setTitle(wl.getTitle());
                        List<WorkContentDto> wcl = getMonthWorkListByPeriod(wl.getYear(),wl.getMonth(),wl.getPeriod(),rv);
                        wcl.add(wct);
                    }
                }
            }
        }
        return rv;
    }

    private List<YearlogDto> getYearlogListByYear(String year,List<TimelineDto> tll){
        List<YearlogDto> rv = new ArrayList<>();
        for (TimelineDto tl: tll
             ) {
            if(tl.getYear().equals(year)){
            rv = tl.getYearlog();
            break;
            }
        }
        return rv;
    }

    private List<MonthWorkDto> getMonthWorkListByYearAndMonth(String year, String month, List<TimelineDto> tll){
        List<MonthWorkDto> rv = new ArrayList<>();
        for (TimelineDto tl: tll
        ) {
            if(tl.getYear().equals(year)){
                for (YearlogDto yl:tl.getYearlog()
                     ) {
                    if(yl.getMonth().equals(month)){
                        rv = yl.getMonthwork();
                        break;
                    }
                }
            }
        }
        return rv;
    }

    private List<WorkContentDto> getMonthWorkListByPeriod(String year, String month, String period,List<TimelineDto> tll){
        List<WorkContentDto> rv = new ArrayList<>();
        for (TimelineDto tl: tll
        ) {
            if(tl.getYear().equals(year)){
                for (YearlogDto yl:tl.getYearlog()
                ) {
                    if(yl.getMonth().equals(month)){
                        for (MonthWorkDto mw :yl.getMonthwork()
                             ) {
                            if(mw.getWorkdate().equals(period)){
                                rv = mw.getWorkcnt();
                                break;
                            }
                        }
                    }
                }
            }
        }
        return rv;
    }
}
