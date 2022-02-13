package br.com.marvel.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.marvel.client.dto.ComicDataWrapper;
import br.com.marvel.client.dto.EventDataWrapper;
import br.com.marvel.client.dto.InlineResponse200;
import br.com.marvel.client.dto.InlineResponse200Data;
import br.com.marvel.client.dto.SeriesDataWrapper;
import br.com.marvel.client.dto.StoryDataWrapper;
import br.com.marvel.client.ports.MarvelClient;
import br.com.marvel.controller.dto.Pagination;
import br.com.marvel.controller.dto.characters.MarvelCharacter;
import br.com.marvel.controller.dto.characters.ThumbnailCharacter;
import br.com.marvel.controller.dto.characters.UrlCharacter;
import br.com.marvel.service.ports.CharacterService;

@Service
public class CharacterServiceImpl implements CharacterService {

	@Autowired
	private MarvelClient client;

	@Override
	public Pagination findCharacters(String name, String nameStartsWith, BigDecimal limit, BigDecimal offset) {
		InlineResponse200 listCharacters = client.listCharacters(name, nameStartsWith, null, null, null, null, null,
				null, limit, offset);
		
		InlineResponse200Data data = listCharacters.getData();
		Pagination pagination = new Pagination();
		pagination.setOffset(data.getOffset());
		pagination.setLimit(data.getLimit());
		pagination.setTotal(data.getTotal());
		pagination.setCount(data.getCount());

		if (!listCharacters.getData().getResults().isEmpty()) {
			List<MarvelCharacter> characters = listCharacters.getData().getResults().stream().map(c -> {
				MarvelCharacter marvelCharacter = new MarvelCharacter();

				marvelCharacter.setId(c.getId());
				marvelCharacter.setName(c.getName());
				marvelCharacter.setDescription(c.getDescription());
				marvelCharacter.setModified(c.getModified());

				ThumbnailCharacter thumbnailCharacter = new ThumbnailCharacter();
				thumbnailCharacter.setUrl(c.getThumbnail().getPath());
				thumbnailCharacter.setExtension(c.getThumbnail().getExtension());

				marvelCharacter.setThumbnail(thumbnailCharacter);

				// @TODO Gravar os Thumbnail no S3 e retornar os links

				// @TODO Gravar as informações dos personagens em um banco de dados

				List<UrlCharacter> urlCharacters = c.getUrls().stream().map(u -> {
					UrlCharacter urlCharacter = new UrlCharacter();
					urlCharacter.setType(u.getType());
					urlCharacter.setUrl(u.getUrl());
					return urlCharacter;
				}).collect(Collectors.toList());

				marvelCharacter.setUrlCharacters(urlCharacters);

				return marvelCharacter;
			}).collect(Collectors.toList());

			pagination.setData(characters);
			return pagination;
		} else {
			return null;
		}
	}

	@Override
	public Pagination findComicsByCharacter(String id, BigDecimal limit, BigDecimal offset) {
		ComicDataWrapper characterComics = client.characterComics(id, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, "-focDate", null, null);

		if (!characterComics.getData().getResults().isEmpty()) {
			// TODO
		}
		return null;
	}

	@Override
	public Pagination findSeriesByCharacter(String id, BigDecimal limit, BigDecimal offset) {
		// TODO Auto-generated method stub
		SeriesDataWrapper characterSeries = client.characterSeries(id, null, null, null, null, null, null, null, null,
				null, null, null, null, null);

		if (!characterSeries.getData().getResults().isEmpty()) {
			// TODO
		}
		return null;
	}

	@Override
	public Pagination findStoriesByCharacter(String id, BigDecimal limit, BigDecimal offset) {
		StoryDataWrapper characterStories = client.characterStories(id, null, null, null, null, null, null, null, null);

		if (!characterStories.getData().getResults().isEmpty()) {
			// TODO
		}
		return null;
	}

	@Override
	public Pagination findEventsByCharacter(String id, BigDecimal limit, BigDecimal offset) {
		EventDataWrapper characterEvents = client.characterEvents(id, null, null, null, null, null, null, null, null,
				null, null);

		if (!characterEvents.getData().getResults().isEmpty()) {
			// TODO
		}
		return null;
	}

}
