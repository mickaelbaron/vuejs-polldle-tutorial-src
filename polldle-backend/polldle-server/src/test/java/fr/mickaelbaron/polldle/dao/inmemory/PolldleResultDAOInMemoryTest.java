package fr.mickaelbaron.polldle.dao.inmemory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import fr.mickaelbaron.polldle.BeanFactory;
import fr.mickaelbaron.polldle.entity.PolldleVoteEntity;

public class PolldleResultDAOInMemoryTest {
    
    @Mock
    private InMemoryFactory refSession;

    @InjectMocks
    private PolldleResultDAOInMemory dao;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getPolldleResultByPathURLTest() {
        // given
        String pathURL = "1";

        PolldleVoteEntity vote1 = BeanFactory.genericPollVoteDBFactory((byte) 1);
        PolldleVoteEntity vote2 = BeanFactory.genericPollVoteDBFactory((byte) 2);
        PolldleVoteEntity vote3 = BeanFactory.genericPollVoteDBFactory((byte) 1);
        PolldleVoteEntity vote4 = BeanFactory.genericPollVoteDBFactory((byte) 1);

        List<PolldleVoteEntity> votes = Arrays.asList(vote1, vote2, vote3, vote4);

        when(refSession.getPollVoteDBByPathUrl(pathURL)).thenReturn(votes);

        // when
        Map<Byte, Integer> result = dao.getPolldleResultByPathURL(pathURL);

        // then
        assertNotNull(result);
        assertEquals(3, (int) result.get((byte) 1));
        assertEquals(1, (int) result.get((byte) 2));
        assertEquals(2, result.size());
    }

    @Test
    public void getPolldleResultByPathURLEmptyTest() {
        // given
        String pathURL = "empty";
        when(refSession.getPollVoteDBByPathUrl(pathURL)).thenReturn(null);

        // when
        Map<Byte, Integer> result = dao.getPolldleResultByPathURL(pathURL);

        // then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}