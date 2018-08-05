package com.huangwu.parse;

import com.huangwu.domain.GrootUser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package: com.huangwu.parse
 * @Author: huangwu
 * @Date: 2018/8/3 20:28
 * @Description:
 * @LastModify:
 */
@Component
public class ExcelResolver implements Resolver {

    /**
     * 解析Excel对象
     *
     * @param sheet    sheet页
     * @param totalNum 行数
     * @return List<GrootUser>
     * @throws Exception
     */
    public List<GrootUser> resolveExcel(Sheet sheet, int totalNum) throws Exception {
        List<GrootUser> users = new ArrayList<>();
        // 第一行是标题不需要解析
        for (int i = 1; i < totalNum; i++) {
            GrootUser user = convertToGrootUser(sheet.getRow(i));
            if (user != null) {
                users.add(user);
            }
        }
        return users;
    }

    /**
     * 将Excel 行数据转成GrootUser对象
     *
     * @param row 行
     * @return GrootUser
     */
    public GrootUser convertToGrootUser(Row row) {
        GrootUser user = new GrootUser();
        String userName = getCell(row, 0).getStringCellValue();
        user.setUserName(userName);
        String phone = getCell(row, 1).getStringCellValue();
        user.setUserPhone(phone);
        String password = getCell(row, 2).getStringCellValue();
        user.setPassword(password);
        String salt = getCell(row, 3).getStringCellValue();
        user.setSalt(salt);
        long roleId = Long.valueOf(getCell(row, 4).getStringCellValue());
        user.setRoleId(roleId);
        int delete = Integer.valueOf(getCell(row, 5).getStringCellValue());
        user.setIsDeleted(delete);
        return user;
    }

    /**
     * 获取cell单元格
     *
     * @param row   行
     * @param index index
     * @return cell
     */
    private Cell getCell(Row row, int index) {
        if (row == null) {
            return null;
        }
        row.getCell(index).setCellType(CellType.STRING);
        return row.getCell(index);
    }



}
