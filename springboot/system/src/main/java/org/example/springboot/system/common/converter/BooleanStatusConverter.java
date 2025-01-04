package org.example.springboot.system.common.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.example.springboot.system.common.enums.BooleanStatus;

/**
 * 状态转换器
 */
public class BooleanStatusConverter implements Converter<Boolean> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return Boolean.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Boolean convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        Boolean code = cellData.getBooleanValue();
        BooleanStatus booleanStatus = BooleanStatus.getByCode(code);
        return booleanStatus.getCode();
    }

    @Override
    public WriteCellData<?> convertToExcelData(Boolean value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        String msg = BooleanStatus.getByCode(value).getMsg();
        return new WriteCellData<>(msg);
    }
}
