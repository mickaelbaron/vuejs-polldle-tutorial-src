package fr.mickaelbaron.polldle.dao.inmemory;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import fr.mickaelbaron.polldle.entity.PolldleVoteEntity;

public class PolldleVoteDAOTest {
    @Mock
    private InMemoryFactory refSession;

    @InjectMocks
    private PolldleVoteDAOInMemory dao; // classe contenant createVote

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createVoteShouldCallRefSession() {
        // given
        String pathUrl = "1";
        PolldleVoteEntity vote = new PolldleVoteEntity();
        vote.setPolldleOptions(Arrays.asList((byte) 1, (byte) 2));

        // when
        dao.createVote(pathUrl, vote);

        // then
        verify(refSession, times(1)).addPollVoteDB(pathUrl, vote);
    }
}
