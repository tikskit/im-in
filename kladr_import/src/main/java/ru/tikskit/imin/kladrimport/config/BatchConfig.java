package ru.tikskit.imin.kladrimport.config;

import org.jamel.kladr.KladrTable;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tikskit.imin.kladrimport.service.RootCountryService;
import ru.tikskit.imin.kladrimport.service.StatesReader;
import ru.tikskit.imin.kladrimport.service.TablesAlterService;

import javax.persistence.EntityManagerFactory;
import java.io.File;

@EnableBatchProcessing
@Configuration
public class BatchConfig {
    public static final String JOB_NAME = "Convert and transfer data from KLADR database to Im in tables";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    private RootCountryService rootCountryService;
    @Autowired
    private TablesAlterService tablesAlterService;


    @Bean
    public Job transferDataJob(Step createCountryRussiaStep, Step transferStatesStep,
                               Step addOuterCodeColumnsToPlacesTablesStep,
                               Step removeOuterCodeColumnsFromPlacesTablesStep) {
        return jobBuilderFactory
                .get(JOB_NAME)
                // подготавливаем
                .start(createCountryRussiaStep)
                .next(addOuterCodeColumnsToPlacesTablesStep)
                // работаем
                .next(transferStatesStep)
                // чистим
                .next(removeOuterCodeColumnsFromPlacesTablesStep)
                .build();
    }

    @StepScope
    @Bean
    public Step createCountryRussiaStep(MethodInvokingTaskletAdapter createCountryRussiaTasklet) {
        return stepBuilderFactory
                .get("createCountryRussiaStep")
                .tasklet(createCountryRussiaTasklet)
                .build();
    }

    @Bean
    public Step addOuterCodeColumnsToPlacesTablesStep(MethodInvokingTaskletAdapter addOuterCodeColumnToStatesTableStep) {
        return stepBuilderFactory
                .get("addOuterCodeColumnsToPlacesTablesStep")
                .tasklet(addOuterCodeColumnToStatesTableStep)
                .build();
    }

    @Bean
    public Step removeOuterCodeColumnsFromPlacesTablesStep(MethodInvokingTaskletAdapter removeOuterCodeColumnFromStatesTable) {
        return stepBuilderFactory
                .get("removeOuterCodeColumnsFromPlacesTablesStep")
                .tasklet(removeOuterCodeColumnFromStatesTable)
                .build();
    }



    @Bean
    public MethodInvokingTaskletAdapter createCountryRussiaTasklet() {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();
        adapter.setTargetObject(rootCountryService);
        adapter.setTargetMethod("insertRootCountry");

        return adapter;
    }

    @Bean
    public MethodInvokingTaskletAdapter addOuterCodeColumnToStatesTableStep() {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();
        adapter.setTargetObject(tablesAlterService);
        adapter.setTargetMethod("addOuterCodeColumnToStatesTable");

        return adapter;
    }

    @Bean
    public MethodInvokingTaskletAdapter removeOuterCodeColumnFromStatesTable() {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();
        adapter.setTargetObject(tablesAlterService);
        adapter.setTargetMethod("removeOuterCodeColumnFromStatesTable");

        return adapter;
    }

    @StepScope
    @Bean
    public Step transferStatesStep(ItemReader<ru.tikskit.imin.kladrimport.model.src.State> stateReader,
                                   ItemProcessor<ru.tikskit.imin.kladrimport.model.src.State, ru.tikskit.imin.kladrimport.model.tar.State> stateProcessor,
                                   ItemWriter<ru.tikskit.imin.kladrimport.model.tar.State> stateWriter) {
        return stepBuilderFactory
                .get("transferStatesStep")
                .<ru.tikskit.imin.kladrimport.model.src.State, ru.tikskit.imin.kladrimport.model.tar.State>chunk(10)
                .reader(stateReader)
                .processor(stateProcessor)
                .writer(stateWriter)
                .build();
    }

    @Bean
    public ItemReader<ru.tikskit.imin.kladrimport.model.src.State> stateReader() {
        return new StatesReader(new File("./" + KladrTable.KLADR.getFileName()));
    }

    @Bean
    public ItemProcessor<ru.tikskit.imin.kladrimport.model.src.State, ru.tikskit.imin.kladrimport.model.tar.State> stateProcessor() {
        return item -> new ru.tikskit.imin.kladrimport.model.tar.State(item.getName(), rootCountryService.getCountry(),
                item.getId());
    }

    @Bean
    public JpaItemWriter<ru.tikskit.imin.kladrimport.model.tar.State> stateWriter() {
        return new JpaItemWriterBuilder<ru.tikskit.imin.kladrimport.model.tar.State>()
                .entityManagerFactory(entityManagerFactory)
                .usePersist(true)
                .build();
    }
}
