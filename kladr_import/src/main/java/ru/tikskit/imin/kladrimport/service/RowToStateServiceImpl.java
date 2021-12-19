package ru.tikskit.imin.kladrimport.service;

import org.jamel.dbf.utils.DbfUtils;
import org.jamel.kladr.data.Region;
import org.jamel.kladr.utils.KladrCodeUtils;
import org.springframework.stereotype.Component;
import ru.tikskit.imin.kladrimport.model.src.State;

@Component
public class RowToStateServiceImpl implements RowToStateService {

    @Override
    public State convert(Object[] row) {
        // Взято из KladrRowProcessor.processRow
        byte[] code = (byte[]) row[2];

        // skip invalid values
        if (!KladrCodeUtils.isValid(code)) {
            return null;
        }

        byte regionId = KladrCodeUtils.getRegionId(code);
        int districtId = KladrCodeUtils.getDistrictId(code);
        int cityId = KladrCodeUtils.getCityId(code);
        int countryId = KladrCodeUtils.getCountryId(code);

        byte[] name = DbfUtils.trimLeftSpaces((byte[]) row[0]);
        byte[] socr = DbfUtils.trimLeftSpaces((byte[]) row[1]);
        int index = DbfUtils.parseInt((byte[]) row[3]);

        // KladrObjectsProcessor.processKladrRow
        if (districtId == 0 && cityId == 0 && countryId == 0) {
            Region region = new Region(name, socr, index);
            return new State(regionId, region.getName());
        }

        return null;
    }
}
