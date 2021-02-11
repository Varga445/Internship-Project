package eu.accesa.learningplatform.service.implementation;

import eu.accesa.learningplatform.model.dto.AnswerDto;
import eu.accesa.learningplatform.model.entity.AnswerEntity;
import eu.accesa.learningplatform.model.entity.QuizItemEntity;
import eu.accesa.learningplatform.repository.AnswerRepository;
import eu.accesa.learningplatform.repository.QuizItemRepository;
import eu.accesa.learningplatform.service.AnswerService;
import eu.accesa.learningplatform.service.exception.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;
    private final QuizItemRepository quizItemRepository;
    private final ModelMapper modelMapper;

    public AnswerServiceImpl(AnswerRepository answerRepository,
                             QuizItemRepository quizItemRepository,
                             ModelMapper modelMapper) {
        this.answerRepository = answerRepository;
        this.quizItemRepository = quizItemRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AnswerDto updateAnswer(AnswerDto answerDto) {
        AnswerEntity answerEntity = answerRepository.findById(answerDto.getId())
                .orElseThrow(() -> new EntityNotFoundException(AnswerEntity.class.getSimpleName(),
                        "id",
                        answerDto.getId().toString()));
        answerEntity.setQuizItem(quizItemRepository.findById(answerDto.getQuizItemId())
                .orElseThrow(() -> new EntityNotFoundException(QuizItemEntity.class.getSimpleName(),
                        "id",
                        answerDto.getQuizItemId().toString())));
        modelMapper.map(answerDto, answerEntity);
        answerRepository.save(answerEntity);
        return modelMapper.map(answerEntity, AnswerDto.class);
    }

    @Override
    public List<AnswerDto> getAllAnswersByQuizItemId(Long id) {
        return modelMapper.map(answerRepository.findAllByQuizItemEntity_Id(id), new TypeToken<List<AnswerDto>>() {
        }.getType());
    }

    @Override
    public AnswerDto createAnswer(AnswerDto answerDto, Long quizItemId) {
        QuizItemEntity quizItemEntity = quizItemRepository.findById(quizItemId)
                .orElseThrow(() -> new EntityNotFoundException(QuizItemEntity.class.getSimpleName(),
                        "id",
                        quizItemId.toString()));
        AnswerEntity answerEntity = modelMapper.map(answerDto, AnswerEntity.class);
        answerEntity.setQuizItem(quizItemEntity);
        AnswerEntity savedAnswer = answerRepository.save(answerEntity);
        return modelMapper.map(savedAnswer, AnswerDto.class);
    }

    @Override
    public void delete(Long id) {
        AnswerEntity answerEntity = answerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(AnswerEntity.class.getSimpleName(),
                        "id",
                        id.toString()));
        answerRepository.delete(answerEntity);
    }
}
