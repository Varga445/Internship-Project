package eu.accesa.learningplatform.service.implementation;

import eu.accesa.learningplatform.model.dto.QuizItemDto;
import eu.accesa.learningplatform.model.entity.AnswerEntity;
import eu.accesa.learningplatform.model.entity.QuizEntity;
import eu.accesa.learningplatform.model.entity.QuizItemEntity;
import eu.accesa.learningplatform.repository.QuizItemRepository;
import eu.accesa.learningplatform.repository.QuizItemTypeRepository;
import eu.accesa.learningplatform.repository.QuizRepository;
import eu.accesa.learningplatform.service.QuizItemService;
import eu.accesa.learningplatform.service.exception.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizItemServiceImpl implements QuizItemService {
    private final QuizItemRepository quizItemRepository;
    private final QuizRepository quizRepository;
    private final QuizItemTypeRepository quizItemTypeRepository;
    private final ModelMapper modelMapper;

    public QuizItemServiceImpl(QuizItemRepository quizItemRepository,
                               QuizRepository quizRepository,
                               QuizItemTypeRepository quizItemTypeRepository,
                               ModelMapper modelMapper) {
        this.quizItemRepository = quizItemRepository;
        this.quizRepository = quizRepository;
        this.quizItemTypeRepository = quizItemTypeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<QuizItemDto> getAllQuizItemsByQuizId(Long id) {
        return modelMapper.map(quizItemRepository.findAllByQuizEntity_Id(id), new TypeToken<List<QuizItemDto>>() {
        }.getType());
    }

    @Override
    public QuizItemDto updateQuizItem(QuizItemDto quizItemDto) {
        QuizItemEntity quizItemEntity = quizItemRepository.findById(quizItemDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(AnswerEntity.class.getSimpleName(),
                        "id",
                        quizItemDto.getId().toString()));
        quizItemEntity.setQuizEntity(quizRepository.findById(quizItemDto.getQuizId())
                .orElseThrow(() -> new EntityNotFoundException(QuizEntity.class.getSimpleName(),
                        "id",
                        quizItemDto.getQuizId().toString())));
        modelMapper.map(quizItemDto, quizItemEntity);
        quizItemRepository.save(quizItemEntity);
        return modelMapper.map(quizItemEntity, QuizItemDto.class);
    }

    @Override
    public QuizItemDto createQuizItem(QuizItemDto quizItemDto, Long quizId) {
        QuizEntity quizEntity = quizRepository.findById(quizId)
                .orElseThrow(() -> new EntityNotFoundException(QuizEntity.class.getSimpleName(),
                        "id",
                        quizId.toString()));
        QuizItemEntity quizItemEntity = modelMapper.map(quizItemDto, QuizItemEntity.class);
        quizItemEntity.setQuizEntity(quizEntity);
        quizItemEntity.setQuizItemType(quizItemTypeRepository.getOne(quizItemDto.getQuizItemTypeId()));
        QuizItemEntity savedQuizItem = quizItemRepository.save(quizItemEntity);
        return modelMapper.map(savedQuizItem, QuizItemDto.class);
    }

    @Override
    public void deleteQuizItem(Long id) {
        QuizItemEntity quizItemEntity = quizItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(QuizItemEntity.class.getSimpleName(),
                        "id",
                        id.toString()));
        quizItemRepository.delete(quizItemEntity);
    }
}
