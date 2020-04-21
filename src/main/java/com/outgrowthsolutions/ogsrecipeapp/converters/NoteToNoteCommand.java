package com.outgrowthsolutions.ogsrecipeapp.converters;

import com.outgrowthsolutions.ogsrecipeapp.commands.NoteCommand;
import com.outgrowthsolutions.ogsrecipeapp.domain.Note;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NoteToNoteCommand implements Converter<Note, NoteCommand> {
    @Override
    public NoteCommand convert(Note note) {
        if (note == null)
            return null;

        final NoteCommand noteCommand = new NoteCommand();
        noteCommand.setId(note.getId());
        noteCommand.setRecipeNotes(note.getRecipeNotes());
        return noteCommand;

    }
}
