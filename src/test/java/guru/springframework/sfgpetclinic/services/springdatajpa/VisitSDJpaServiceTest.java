package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    private VisitRepository mockRepo;

    @InjectMocks
    private VisitSDJpaService service;

    @Test
    void findAll() {

        // given
        List<Visit> dummyData = List.of(new Visit(0L), new Visit(1L));
        when(mockRepo.findAll()).thenReturn(dummyData);

        Visit nonPresentData = new Visit(2L);

        // when
        Set<Visit> output = service.findAll();

        // then
        verify(mockRepo).findAll();

        dummyData.forEach(visit -> assertTrue(output.contains(visit)));

        assertFalse(output.contains(nonPresentData));
    }

    @Test
    void findById() {

        // Non null case
        // given
        var id = 0L;
        var dummyData = new Visit(id);

        // when
        when(mockRepo.findById(id)).thenReturn(Optional.of(dummyData));
        Visit output = service.findById(id);

        // then
        verify(mockRepo).findById(id);
        assertEquals(dummyData, output);

        // Null case
        // given
        var nonExistingId = 404L;

        // when
        when(mockRepo.findById(nonExistingId)).thenReturn(Optional.empty());
        Visit nullOutput = service.findById(nonExistingId);

        // then
        verify(mockRepo).findById(nonExistingId);
        assertNull(nullOutput);
    }

    @Test
    void save() {

        // given
        var id = 0L;
        var inputDummyData = new Visit();
        var outputDummyData = new Visit(id);

        // when
        when(mockRepo.save(inputDummyData)).thenReturn(outputDummyData);
        Visit output = service.save(inputDummyData);

        // then
        verify(mockRepo).save(inputDummyData);
        assertEquals(outputDummyData, output);
    }

    @Test
    void delete() {

        // given
        var id = 0L;
        var dummyData = new Visit(id);

        // when
        service.delete(dummyData);

        // then
        verify(mockRepo).delete(dummyData);
    }

    @Test
    void deleteById() {

        // given
        var id = 0L;

        // when
        service.deleteById(id);

        // then
        verify(mockRepo).deleteById(id);
    }
}