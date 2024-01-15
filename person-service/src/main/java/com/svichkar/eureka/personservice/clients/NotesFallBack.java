package com.svichkar.eureka.personservice.clients;

import org.springframework.stereotype.Component;

@Component
public class NotesFallBack implements NotesClient {

    @Override
    public String getNotes(final String correlationId) {
        return null;
    }
}
