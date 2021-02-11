package eu.accesa.learningplatform.service.implementation;

import ch.qos.logback.classic.spi.LoggingEvent;
import eu.accesa.learningplatform.model.dto.QuizItemDto;
import eu.accesa.learningplatform.model.entity.QuizEntity;
import eu.accesa.learningplatform.model.entity.QuizItemEntity;
import eu.accesa.learningplatform.model.entity.QuizItemTypeEntity;
import eu.accesa.learningplatform.model.entity.QuizItemTypeEnum;
import eu.accesa.learningplatform.repository.QuizItemRepository;
import eu.accesa.learningplatform.repository.QuizItemTypeRepository;
import eu.accesa.learningplatform.repository.QuizRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static eu.accesa.learningplatform.utils.TestUtils.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(SpringRunner.class)
public class QuizItemServiceImplTest {
    @Mock
    private QuizItemRepository quizItemRepository;
    @Mock
    private QuizRepository quizRepository;
    @Mock
    private QuizItemTypeRepository quizItemTypeRepository;
    @Mock
    private Logger LOGGER = LoggerFactory.getLogger(LessonServiceImplTest.class);
    @Spy
    private ModelMapper mapper;
    @Captor
    private ArgumentCaptor<LoggingEvent> loadArgumentCaptor;
    @InjectMocks
    private QuizItemServiceImpl quizItemService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllQuizItemsByQuizId() {
        Long quizId = 1L;
        when(quizItemRepository.findAllByQuizEntity_Id(quizId)).thenReturn(testQuizItemList().stream().collect(Collectors.toList()));
        final List<QuizItemDto> quizItemDtoList = quizItemService.getAllQuizItemsByQuizId(quizId);
        assertNotNull(quizItemDtoList, "List is empty");
        assertEquals(quizItemDtoList.size(), 2, "List size doesn't match actual size");
        QuizItemDto quizItemDto = quizItemDtoList.get(0);
        assertNotNull(quizItemDto.getId());
        verify(quizItemRepository).findAllByQuizEntity_Id(quizId);
        verifyNoMoreInteractions(quizItemRepository);
    }

    @Test
    public void updateQuizItem() {
        QuizEntity quizEntity = testQuizEntity(1L, null, null, null);
        QuizItemTypeEntity quizItemTypeEntity = testQuizItemType(1L, null, null);
        QuizItemEntity quizItemEntity = testQuizItemEntity(1L, "What is Spring?", quizItemTypeEntity, null, quizEntity);

        QuizEntity updatedQuizEntity = testQuizEntity(1L, null, null, null);
        QuizItemTypeEntity updatedQuizItemTypeEntity = testQuizItemType(1L, null, null);
        QuizItemEntity updatedQuizItemEntity =testQuizItemEntity( 1L, "What is ORM?", updatedQuizItemTypeEntity, null, updatedQuizEntity);

        when(quizItemRepository.findById(quizItemEntity.getId())).thenReturn(Optional.of(quizItemEntity));
        when(quizRepository.findById(quizItemEntity.getQuizEntity().getId())).thenReturn(Optional.of(quizEntity));
        when(quizItemTypeRepository.findById(quizItemEntity.getQuizItemTypeEntity().getId())).thenReturn(Optional.of(quizItemTypeEntity));
        when(quizItemRepository.save(quizItemEntity)).thenReturn(updatedQuizItemEntity);

        QuizItemDto quizItemDto = testQuizItemDto(updatedQuizItemEntity.getId(), updatedQuizItemEntity.getQuizEntity().getId(), updatedQuizItemEntity.getQuizItemTypeEntity().getId(), updatedQuizItemEntity.getQuestion(), null);
        QuizItemDto updatedQuizItemDto = quizItemService.updateQuizItem(quizItemDto);
        assertNotNull(updatedQuizItemDto);
    }

    @Test
    public void createQuizItem() {
        QuizEntity quizEntity = testQuizEntity(1L, null, null, null);
        QuizItemTypeEntity quizItemTypeEntity = testQuizItemType(1L, QuizItemTypeEnum.SINGLE_CHOICE, null);
        QuizItemEntity quizItemEntity = testQuizItemEntity(null, "What is spring?", quizItemTypeEntity, null, quizEntity);
        QuizItemEntity createdQuizItemEntity = testQuizItemEntity(1L, "What is spring?", quizItemTypeEntity, null, quizEntity);

        QuizItemDto quizItemDto = testQuizItemDto(null, 1L, 1L, "What is spring?", null);

        when(quizRepository.findById(quizItemEntity.getQuizEntity().getId())).thenReturn(Optional.of(quizEntity));
        when(quizItemTypeRepository.getOne(quizItemEntity.getQuizItemTypeEntity().getId())).thenReturn(quizItemTypeEntity);
        when(quizItemRepository.save(quizItemEntity)).thenReturn(createdQuizItemEntity);

        QuizItemDto createdQuizItemDto = quizItemService.createQuizItem(quizItemDto, quizItemDto.getQuizId());

        assertNotNull(createdQuizItemDto, "Created quizItem can not be null");
        assertNotNull(createdQuizItemDto.getId(), "Id can not be null");
        assertEquals(createdQuizItemDto.getQuestion(), "What is spring?");
        assertEquals(createdQuizItemDto.getQuizItemTypeId(), 1l);
        verify(quizItemRepository).save(quizItemEntity);
        verifyNoMoreInteractions(quizItemRepository);
    }

    @Test
    public void deleteQuizItem() {
        Long id = 1l;
        QuizEntity quizEntity = testQuizEntity(1L, null, null, null);
        QuizItemEntity quizItemEntity = testQuizItemEntity(1L, "What is spring?", null, null, quizEntity);
        when(quizItemRepository.findById(id)).thenReturn(Optional.of(quizItemEntity));
        quizItemService.deleteQuizItem(id);
    }
}