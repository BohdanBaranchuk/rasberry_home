package com.homedev.smart_home.smart89.v1.database.repository;

import com.homedev.smart_home.smart89.v1.database.domain.AirDatabaseModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
public class AirDatabaseModelRepositoryTest {

    @MockBean
    private AirDatabaseModelRepository repository;

    @Before
    public void before() {
        System.out.println("Before");
    }

    @Test
    public void getUserByNameTest() {

        AirDatabaseModel model = new AirDatabaseModel();

        model.setId(1L);
        model.setRoomName("test");
        model.setModeName("ON");
        model.setDesiredTemperature(25F);

        given(this.repository.findAirByRoomName("test")).willReturn(model);

        AirDatabaseModel testMockedModel = repository.findAirByRoomName("test");

        assertThat(testMockedModel.getDesiredTemperature()).isEqualTo(25);
        assertThat(testMockedModel.getId()).isEqualTo(1);
        assertThat(testMockedModel.getModeName()).isEqualTo("ON");
        assertThat(testMockedModel.getRoomName()).isEqualTo("test");
    }

}
