package com.xd.service;

import com.xd.vo.OrderReportVO;
import com.xd.vo.SalesTop10ReportVO;
import com.xd.vo.TurnoverReportVO;
import com.xd.vo.UserReportVO;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

public interface ReportService {

    /**
     * 统计指定时间区间内的营业额数据
     * @param begin
     * @param end
     * @return
     */
    TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end);

    /**
     * 统计指定时间区间内的用户数据
     * @param begin
     * @param end
     * @return
     */
    UserReportVO getUserStatistics(LocalDate begin, LocalDate end);

    /**
     * 统计指定时间区间内的订单数据
     * @param begin
     * @param end
     * @return
     */
    OrderReportVO getOrderStatistics(LocalDate begin, LocalDate end);

    /**
     * 统计指定时间区间内的销量top10数据
     * @param begin
     * @param end
     * @return
     */
    SalesTop10ReportVO getSalesTop10Statistics(LocalDate begin, LocalDate end);

    /**
     * 导出运营数据报表
     * @param response
     */
    void exportBusinessData(HttpServletResponse response);
}
