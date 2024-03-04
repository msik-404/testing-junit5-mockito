package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    private OwnerService serviceMock;

    @InjectMocks
    private OwnerController controller;

    @Mock
    private BindingResult bindingResultMock;

    @Test
    void processCreationFormHasNoErrors() {

        // given
        var id = 0L;
        var name = "name";
        var lastName = "last name";

        var inputDummyData = new Owner(null, name, lastName);
        var outputDummyData = new Owner(id, name, lastName);

        var expected = "redirect:/owners/" + id;

        given(bindingResultMock.hasErrors()).willReturn(false);
        given(serviceMock.save(inputDummyData)).willReturn(outputDummyData);

        // when
        var output = controller.processCreationForm(inputDummyData, bindingResultMock);

        // then
        then(serviceMock).should().save(inputDummyData);

        assertThat(output).isEqualTo(expected);
    }

    @Test
    void processCreationFormHasErrors() {

        // given
        var name = "name";
        var lastName = "last name";

        var inputDummyData = new Owner(null, name, lastName);

        var expected = "owners/createOrUpdateOwnerForm";

        given(bindingResultMock.hasErrors()).willReturn(true);

        // when
        var output = controller.processCreationForm(inputDummyData, bindingResultMock);

        // then
        then(serviceMock).shouldHaveZeroInteractions();

        assertThat(output).isEqualTo(expected);
    }
}