package com.outgrowthsolutions.ogsrecipeapp.converters;

import com.outgrowthsolutions.ogsrecipeapp.commands.NoteCommand;
import com.outgrowthsolutions.ogsrecipeapp.domain.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NoteToNoteCommandTest {

    @InjectMocks
    NoteToNoteCommand converter;

    @BeforeEach
    void setUp() {
//        converter = new NoteToNoteCommand();
    }

    @Test
    void convertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmpty() {
        assertNotNull(converter.convert(new Note()));
    }

    @Test
    void convert(){
        String id = "1L";
        String recipeNotes = "This is a test note.";

        Note note = new Note();
        note.setId(id);
        note.setRecipeNotes(recipeNotes);

        NoteCommand noteCommand = converter.convert(note);

        assertNotNull(noteCommand);
        assertEquals(id,noteCommand.getId());
        assertEquals(recipeNotes,noteCommand.getRecipeNotes());
    }
}