package br.com.marvel.service.ports;

import java.math.BigDecimal;

import br.com.marvel.controller.dto.Pagination;

public interface CharacterService {

	Pagination findCharacters(String name, String nameStartsWith, BigDecimal limit, BigDecimal offset);

	Pagination findComicsByCharacter(String id, BigDecimal limit, BigDecimal offset);
	
	Pagination findSeriesByCharacter(String id, BigDecimal limit, BigDecimal offset);
	
	Pagination findStoriesByCharacter(String id, BigDecimal limit, BigDecimal offset);

	Pagination findEventsByCharacter(String id, BigDecimal limit, BigDecimal offset);

}