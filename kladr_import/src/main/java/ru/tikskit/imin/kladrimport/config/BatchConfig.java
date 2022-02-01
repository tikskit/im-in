package ru.tikskit.imin.kladrimport.config;

import lombok.RequiredArgsConstructor;
import org.jamel.dbf.DbfReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import ru.tikskit.imin.kladrimport.service.RootCountryService;
import ru.tikskit.imin.kladrimport.service.StatesReader;
import ru.tikskit.imin.kladrimport.service.TablesAlterService;

import javax.persistence.EntityManagerFactory;
import java.io.File;

@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {
    public static final String JOB_NAME = "ImportFromKLADR";

    private final JobLauncher jobLauncher;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final RootCountryService rootCountryService;
    private final TablesAlterService tablesAlterService;

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
    public ItemReader<ru.tikskit.imin.kladrimport.model.src.State> stateReader(
            @Value("#{jobParameters[file.name]}") String filename) {
        return new StatesReader(new DbfReader(new File(filename)));
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
/*

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder, DataSource pgDataSource) {
        return builder
                .dataSource(pgDataSource)
                .packages("ru.tikskit.imin.kladrimport.model.tar")
                .build();
    }
*/

}
