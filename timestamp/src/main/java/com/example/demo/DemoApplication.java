package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@EnableTask
@SpringBootApplication
@Slf4j
@EnableBatchProcessing
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Configuration
	public class JobConfiguration {

		private final static int DEFAULT_CHUNK_SIZE = 3;

		@Autowired
		JobBuilderFactory jobBuilderFactory;

		@Autowired
		StepBuilderFactory stepBuilderFactory;

		@Bean
		public Step step1() {
			return stepBuilderFactory.get("step 1")
					.tasklet(new Tasklet() {
						@Override
						public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
							System.out.println("Tasklet has run");
							return RepeatStatus.FINISHED;
						}
					}).build();
		}

		@Bean
		public Step step2() {

			return stepBuilderFactory.get("step 2")
					.<String, String>chunk(DEFAULT_CHUNK_SIZE)
					.reader(new ListItemReader<>(Arrays.asList("1","2","3","4","5","6" )))
					.processor(new ItemProcessor<String, String>() {
						@Override
						public String process(String item) throws Exception {
							return String.valueOf(Integer.valueOf(item) * -1) ;
						}
					})
					.writer(new ItemWriter<String>() {
						@Override
						public void write(List<? extends String> items) throws Exception {
							items.forEach(s -> System.out.println(">>" + s));
						}
					})
					.build();
		}

		@Bean
		public Job job() {
			return jobBuilderFactory.get("job")
					.start(step1())
					.next(step2())
					.build();
		}
	}
	/*@Bean
	public TimeStampTask task() {
		return new TimeStampTask();
	}

	class TimeStampTask implements CommandLineRunner {

		@Autowired
		TimeStampTaskProperties properties;

		@Override
		public void run(String... args) throws Exception {
			var format = new SimpleDateFormat(this.properties.getForamt());

			log.info(format.format(new Date()));
		}
	}*/
}
