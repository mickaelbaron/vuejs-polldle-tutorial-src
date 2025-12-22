package fr.mickaelbaron.polldle.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.mickaelbaron.polldle.BeanFactory;
import fr.mickaelbaron.polldle.dao.IPolldleDAO;
import fr.mickaelbaron.polldle.entity.PolldleEntity;
import fr.mickaelbaron.polldle.model.Polldle;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ResourceContext;

@ExtendWith(MockitoExtension.class)
public class PolldlesResourceImplTest {

    @Mock
    private IPolldleDAO pollDAO;

    @Mock
    private ResourceContext resourceContext;

    @InjectMocks
    private PolldlesResourceImpl resource;

    // -----------------------
    // getPolldleByPathURL
    // -----------------------

    @Test
    void getPolldleByPathURL_success() {
        // given
        PolldleEntity pollEntity = BeanFactory.genericPollDBFactory();
        when(pollDAO.getPolldleByPathURL("1"))
                .thenReturn(Optional.of(pollEntity));

        // when
        Polldle result = resource.getPolldleByPathURL("1");

        // then
        assertNotNull(result);
        assertNull(result.getKey()); // données privées supprimées
        verify(pollDAO).getPolldleByPathURL("1");
    }

    @Test
    void getPolldleByPathURL_nullParameter() {
        // given

        // when / then
        WebApplicationException ex = assertThrows(WebApplicationException.class,
                () -> resource.getPolldleByPathURL(null));

        assertEquals("Path URL is missing.", ex.getMessage());
        verifyNoInteractions(pollDAO);
    }

    @Test
    void getPolldleByPathURL_notFound() {
        // given
        when(pollDAO.getPolldleByPathURL("1"))
                .thenReturn(Optional.empty());

        // when / then
        WebApplicationException ex = assertThrows(WebApplicationException.class,
                () -> resource.getPolldleByPathURL("1"));

        assertEquals("No Polldle with this path URL.", ex.getMessage());
    }

    // -----------------------
    // createPolldle
    // -----------------------

    @Test
    void createPoll_success() {
        // given
        Polldle poll = BeanFactory.genericPollFactory();
        PolldleEntity entity = BeanFactory.genericPollDBFactory();
        entity.setPathUrl("1");

        when(pollDAO.createPolldle(any()))
                .thenReturn(entity);

        // when
        Polldle created = resource.createPolldle(poll);

        // then
        assertNotNull(created);
        assertEquals("1", created.getPathUrl());
        verify(pollDAO).createPolldle(any());
    }

    @Test
    void createPoll_daoReturnsNull() {
        Polldle poll = BeanFactory.genericPollFactory();
        when(pollDAO.createPolldle(any())).thenReturn(null);

        WebApplicationException ex =
                assertThrows(WebApplicationException.class,
                        () -> resource.createPolldle(poll));

        assertEquals("HTTP 500 Internal Server Error", ex.getMessage());
        verify(pollDAO).createPolldle(any());
    }

    @Test
    void createPoll_withoutQuestion() {
        Polldle poll = BeanFactory.genericPollFactory();
        poll.setQuestion(null);

        WebApplicationException ex =
                assertThrows(WebApplicationException.class,
                        () -> resource.createPolldle(poll));

        assertEquals("Question is missing.", ex.getMessage());
        verifyNoInteractions(pollDAO);
    }

    @Test
    void createPoll_withoutPollOptions() {
        Polldle poll = BeanFactory.genericPollFactory();
        poll.setPolldleOptions(null);

        WebApplicationException ex =
                assertThrows(WebApplicationException.class,
                        () -> resource.createPolldle(poll));

        assertEquals(
            "Polldle option can not be null and must contain at least two elements.",
            ex.getMessage()
        );
        verifyNoInteractions(pollDAO);
    }
}
