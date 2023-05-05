package it.snorcini.dev.orderplanner.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Tests regarding the ActivitiesDTO class")
class ActivitiesDTOTest {

    private ActivitiesDTO activitiesDTO;

    @Test
    @DisplayName("Should not produce a null toString outcome")
    void testActivitiesDTO01() {
        activitiesDTO = new ActivitiesDTO();
        String toStringed = activitiesDTO.toString();
        assertThat(toStringed).overridingErrorMessage("This element should not be null").isNotNull();
    }

    @Test
    @DisplayName("Should match when created with builder and constructor")
    void testActivitiesDTO02() {
        OffsetDateTime NOW = OffsetDateTime.now();

        activitiesDTO = new ActivitiesDTO(OffsetDateTime.MIN, NOW, "lastUserModify");
        ActivitiesDTO activitiesDTOSetter = ActivitiesDTO.builder().dateInsert(OffsetDateTime.MIN).dateModify(NOW)
                .lastUserModify("lastUserModify").build();
        assertEquals(activitiesDTO, activitiesDTOSetter, "These objects should be equal");
    }

    @Test
    @DisplayName("Should set each field using setters")
    void testActivitiesDTO03() {
        // given
        activitiesDTO = new ActivitiesDTO();
        final OffsetDateTime dateInsertMock = OffsetDateTime.now();
        final OffsetDateTime dateModifyMock = OffsetDateTime.now();
        final String lastUserModifyMock = "lastUserModifyMock";

        // when
        activitiesDTO.setDateInsert(dateInsertMock);
        activitiesDTO.setDateModify(dateModifyMock);
        activitiesDTO.setLastUserModify(lastUserModifyMock);

        // then
        assertEquals(dateInsertMock, activitiesDTO.getDateInsert(), "These objects should be equal");
        assertEquals(dateModifyMock, activitiesDTO.getDateModify(), "These objects should be equal");
        assertEquals(lastUserModifyMock, activitiesDTO.getLastUserModify(), "These objects should be equal");
    }

}
