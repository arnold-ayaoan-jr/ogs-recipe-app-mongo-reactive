package com.outgrowthsolutions.ogsrecipeapp.converters;

import com.outgrowthsolutions.ogsrecipeapp.commands.NoteCommand;
import com.outgrowthsolutions.ogsrecipeapp.domain.Note;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NoteCommandToNote implements Converter<NoteCommand, Note> {
    @Override
    public Note convert(NoteCommand noteCommand) {

        if (noteCommand == null)
            return null;

        final Note note = new Note();
        note.setId(noteCommand.getId());
        note.setRecipeNotes(noteCommand.getRecipeNotes());
        return note;
    }
}
