package com.outgrowthsolutions.ogsrecipeapp.converters;

import com.outgrowthsolutions.ogsrecipeapp.commands.NoteCommand;
import com.outgrowthsolutions.ogsrecipeapp.domain.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteCommandToNoteTest {
    NoteCommandToNote converter;

    @BeforeEach
    void setUp() {
        converter=new NoteCommandToNote();
    }

    @Test
    void convertNull() {
        assertNull(converter.convert(null));
    }

    @Test
    void convertEmpty() {
        assertNotNull(converter.convert(new NoteCommand()));
    }

    @Test
    void convert() {
        NoteCommand noteCommand = new NoteCommand();
        String id = "1L";
        noteCommand.setId(id);
        String recipeNotes = "This is a note.";
        noteCommand.setRecipeNotes(recipeNotes);

        Note note = converter.convert(noteCommand);

        assertNotNull(note);
        assertEquals(id,note.getId());
        assertEquals(recipeNotes,note.getRecipeNotes());
    }
}