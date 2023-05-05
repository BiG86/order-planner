package it.snorcini.dev.orderplanner.dto;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DetailOrderDTOTest {

    private DetailOrderDTO detailBookDTO;

    @Test
    void testDetailBookDTO01() {
        detailBookDTO = new DetailOrderDTO();
        String toStringed = detailBookDTO.toString();
        assertThat(toStringed).overridingErrorMessage("This element should not be null").isNotNull();
    }

    @Test
    void testDetailBookDTO02() {
        OffsetDateTime now = OffsetDateTime.now();
        detailBookDTO = new DetailOrderDTO(ActivitiesDTO.builder().dateInsert(OffsetDateTime.MIN).dateModify(now)
                .lastUserModify("lastUserModify").build());
        DetailOrderDTO detailBookDTOSetter = new DetailOrderDTO();
        detailBookDTOSetter.setActivities(ActivitiesDTO.builder().dateInsert(OffsetDateTime.MIN).dateModify(now)
                .lastUserModify("lastUserModify").build());
        assertEquals(detailBookDTOSetter, detailBookDTO, "These objects should be equal");
    }

}
