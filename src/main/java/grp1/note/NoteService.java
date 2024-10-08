package grp1.note;

import grp1.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.text.MessageFormat.format;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    public static final int NOTE_TITLE_MIN_LENGTH = 5;
    public static final int NOTE_TITLE_MAX_LENGTH = 100;
    public static final int NOTE_CONTENT_MIN_LENGTH = 5;
    public static final int NOTE_CONTENT_MAX_LENGTH = 10000;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void deleteById(String id) {
        noteRepository.deleteById(id);
    }

    public void save(Note note) {

        validateNote(note);

        noteRepository.save(note);
    }

    public Optional<Note> getById(String id) {
        return noteRepository.findById(id);
    }

    public List<Note> findByUser(User user) {
        return noteRepository.findByUser(user);
    }

    public List<Note> listNoteByContent(Long userId, String content) {
        return noteRepository.findbyContentList(userId, "%" + content + "%");
    }

    private void validateNote(Note note) {
        if (note.getTitle().length() < NOTE_TITLE_MIN_LENGTH || note.getTitle().length() >= NOTE_TITLE_MAX_LENGTH) {
            throw new IllegalArgumentException(format("Note title should be between {0} and {1} characters", NOTE_TITLE_MIN_LENGTH, NOTE_TITLE_MAX_LENGTH));
        }

        if (note.getContent().length() < NOTE_CONTENT_MIN_LENGTH || note.getContent().length() >= NOTE_CONTENT_MAX_LENGTH) {
            throw new IllegalArgumentException(format("Note content should be between {0} and {1}  characters", NOTE_CONTENT_MIN_LENGTH, NOTE_CONTENT_MAX_LENGTH));
        }
    }
}
