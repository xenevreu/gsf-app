package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.GsfApp;

import io.github.jhipster.application.domain.ReinfControle;
import io.github.jhipster.application.repository.ReinfControleRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ReinfControleResource REST controller.
 *
 * @see ReinfControleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GsfApp.class)
public class ReinfControleResourceIntTest {

    private static final String DEFAULT_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_EMPRESA = "BBBBBBBBBB";

    @Autowired
    private ReinfControleRepository reinfControleRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restReinfControleMockMvc;

    private ReinfControle reinfControle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReinfControleResource reinfControleResource = new ReinfControleResource(reinfControleRepository);
        this.restReinfControleMockMvc = MockMvcBuilders.standaloneSetup(reinfControleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReinfControle createEntity(EntityManager em) {
        ReinfControle reinfControle = new ReinfControle()
            .empresa(DEFAULT_EMPRESA);
        return reinfControle;
    }

    @Before
    public void initTest() {
        reinfControle = createEntity(em);
    }

    @Test
    @Transactional
    public void createReinfControle() throws Exception {
        int databaseSizeBeforeCreate = reinfControleRepository.findAll().size();

        // Create the ReinfControle
        restReinfControleMockMvc.perform(post("/api/reinf-controles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reinfControle)))
            .andExpect(status().isCreated());

        // Validate the ReinfControle in the database
        List<ReinfControle> reinfControleList = reinfControleRepository.findAll();
        assertThat(reinfControleList).hasSize(databaseSizeBeforeCreate + 1);
        ReinfControle testReinfControle = reinfControleList.get(reinfControleList.size() - 1);
        assertThat(testReinfControle.getEmpresa()).isEqualTo(DEFAULT_EMPRESA);
    }

    @Test
    @Transactional
    public void createReinfControleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reinfControleRepository.findAll().size();

        // Create the ReinfControle with an existing ID
        reinfControle.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReinfControleMockMvc.perform(post("/api/reinf-controles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reinfControle)))
            .andExpect(status().isBadRequest());

        // Validate the ReinfControle in the database
        List<ReinfControle> reinfControleList = reinfControleRepository.findAll();
        assertThat(reinfControleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllReinfControles() throws Exception {
        // Initialize the database
        reinfControleRepository.saveAndFlush(reinfControle);

        // Get all the reinfControleList
        restReinfControleMockMvc.perform(get("/api/reinf-controles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reinfControle.getId().intValue())))
            .andExpect(jsonPath("$.[*].empresa").value(hasItem(DEFAULT_EMPRESA.toString())));
    }
    
    @Test
    @Transactional
    public void getReinfControle() throws Exception {
        // Initialize the database
        reinfControleRepository.saveAndFlush(reinfControle);

        // Get the reinfControle
        restReinfControleMockMvc.perform(get("/api/reinf-controles/{id}", reinfControle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reinfControle.getId().intValue()))
            .andExpect(jsonPath("$.empresa").value(DEFAULT_EMPRESA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReinfControle() throws Exception {
        // Get the reinfControle
        restReinfControleMockMvc.perform(get("/api/reinf-controles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReinfControle() throws Exception {
        // Initialize the database
        reinfControleRepository.saveAndFlush(reinfControle);

        int databaseSizeBeforeUpdate = reinfControleRepository.findAll().size();

        // Update the reinfControle
        ReinfControle updatedReinfControle = reinfControleRepository.findById(reinfControle.getId()).get();
        // Disconnect from session so that the updates on updatedReinfControle are not directly saved in db
        em.detach(updatedReinfControle);
        updatedReinfControle
            .empresa(UPDATED_EMPRESA);

        restReinfControleMockMvc.perform(put("/api/reinf-controles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedReinfControle)))
            .andExpect(status().isOk());

        // Validate the ReinfControle in the database
        List<ReinfControle> reinfControleList = reinfControleRepository.findAll();
        assertThat(reinfControleList).hasSize(databaseSizeBeforeUpdate);
        ReinfControle testReinfControle = reinfControleList.get(reinfControleList.size() - 1);
        assertThat(testReinfControle.getEmpresa()).isEqualTo(UPDATED_EMPRESA);
    }

    @Test
    @Transactional
    public void updateNonExistingReinfControle() throws Exception {
        int databaseSizeBeforeUpdate = reinfControleRepository.findAll().size();

        // Create the ReinfControle

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReinfControleMockMvc.perform(put("/api/reinf-controles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reinfControle)))
            .andExpect(status().isBadRequest());

        // Validate the ReinfControle in the database
        List<ReinfControle> reinfControleList = reinfControleRepository.findAll();
        assertThat(reinfControleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReinfControle() throws Exception {
        // Initialize the database
        reinfControleRepository.saveAndFlush(reinfControle);

        int databaseSizeBeforeDelete = reinfControleRepository.findAll().size();

        // Get the reinfControle
        restReinfControleMockMvc.perform(delete("/api/reinf-controles/{id}", reinfControle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ReinfControle> reinfControleList = reinfControleRepository.findAll();
        assertThat(reinfControleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReinfControle.class);
        ReinfControle reinfControle1 = new ReinfControle();
        reinfControle1.setId(1L);
        ReinfControle reinfControle2 = new ReinfControle();
        reinfControle2.setId(reinfControle1.getId());
        assertThat(reinfControle1).isEqualTo(reinfControle2);
        reinfControle2.setId(2L);
        assertThat(reinfControle1).isNotEqualTo(reinfControle2);
        reinfControle1.setId(null);
        assertThat(reinfControle1).isNotEqualTo(reinfControle2);
    }
}
