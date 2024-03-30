package com.is.client;

import java.io.PrintStream;
import java.time.Duration;

import org.springframework.web.reactive.function.client.WebClient;

import com.is.client.models.Professor;
import com.is.client.models.Student;
import com.is.client.models.StudentProfessor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuples;
import reactor.util.retry.Retry;

public class ClientWeb {

	public final String FILE_1 = "assignment1.txt";
	public final String FILE_2 = "assignment2.txt";
	public final String FILE_3 = "assignment3.txt";
	public final String FILE_4 = "assignment4.txt";
	public final String FILE_5 = "assignment5.txt";
	public final String FILE_6 = "assignment6.txt";
	public final String FILE_7 = "assignment7.txt";
	public final String FILE_8 = "assignment8.txt";
	public final String FILE_9 = "assignment9.txt";
	public final String FILE_10 = "assignment10.txt";
	public final String FILE_11 = "assignment11.txt";

	WebClient webClient = WebClient.create("http://localhost:8080");

	public void requirements() throws InterruptedException {

		requirement_1();
		Thread.sleep(100);

		requirement_2();
		Thread.sleep(100);

		requirement_3();
		Thread.sleep(100);

		requirement_4();
		Thread.sleep(100);

		requirement_5();
		Thread.sleep(100);

		requirement_6();
		Thread.sleep(100);

		requirement_7();
		Thread.sleep(100);

		requirement_8();
		Thread.sleep(100);

		requirement_9();
		Thread.sleep(100);

		requirement_10();
		Thread.sleep(100);

		requirement_11();
		Thread.sleep(200);

		error();
	}

	public void error() {
		try{
		webClient.get().uri("/error").retrieve().bodyToFlux(Student.class)
				.retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1))).doOnError(e -> System.out.println("ERROR"))
				.blockLast();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}

	public void requirement_1() {
		try {
			PrintStream ps = new PrintStream(FILE_1);

			Flux<Student> studentFlux = webClient.get().uri("/students").retrieve().bodyToFlux(Student.class)
					.retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1)));
			studentFlux
					.doOnError(e -> System.out.println("Error on requirement 1"))
					.doFinally(x -> ps.close())
					.subscribe(s -> {
						ps.println("Name: " + s.getName() + " " + "BirthDate: " + s.getBirthDate());
						System.out.println("Name: " + s.getName() + " " + "BirthDate: " + s.getBirthDate());
					});

		} catch (Exception e) {
			System.out.println("An exception has occurred on requirement 1");
		}
	}

	public void requirement_2() {
		try {

			PrintStream ps = new PrintStream(FILE_2);

			Flux<Student> studentFlux = webClient.get().uri("/students").retrieve().bodyToFlux(Student.class)
					.retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1)))
					.retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1)));

			studentFlux
					.doOnError(e -> System.out.println("Error on requirement 2"))
					.doFinally(x -> ps.close())
					.count()
					.subscribe(t -> {
						ps.println("Number of students: " + t);
						System.out.println("Number of students: " + t);
					});

		} catch (Exception e) {
			System.out.println("An exception has occurred on requirement 2");
		}
	}

	public void requirement_3() {
		try {

			PrintStream ps = new PrintStream(FILE_3);

			Flux<Student> studentFlux = webClient.get().uri("/students").retrieve().bodyToFlux(Student.class)
					.retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1)));

			studentFlux
					.doOnError(e -> System.out.println("Error on requirement 3"))
					.onErrorComplete()
					.doFinally(x -> ps.close())
					.filter(s -> s.getCompletedCredits() < 180)
					.count()
					.subscribe(t -> {
						ps.println("Number of active students: " + t);
						System.out.println("Number of active students: " + t);
					});

		} catch (Exception e) {
			System.out.println("An exception has occurred on requirement 3");
		}

	}

	public void requirement_4() {

		try {
			PrintStream ps = new PrintStream(FILE_4);

			Flux<Student> studentFlux = webClient.get().uri("/students").retrieve().bodyToFlux(Student.class)
					.retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1)));

			studentFlux
					.doOnError(e -> System.out.println("Error on requirement 4"))
					.doFinally(x -> ps.close())
					.map(s -> s.getCompletedCredits() / 6)
					.reduce(0, (acc, val) -> acc + val)
					.subscribe(t -> {
						System.out.println("Number of credits: " + t);
						ps.println("Number of credits: " + t);
					});

		} catch (Exception e) {
			System.out.println("An exception has occurred on requirement 4");
		}
	}

	public void requirement_5() {
		try {
			PrintStream ps = new PrintStream(FILE_5);

			Flux<Student> studentFlux = webClient.get().uri("/students").retrieve().bodyToFlux(Student.class)
					.retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1)));

			studentFlux
					.doOnError(e -> System.out.println("Error on requirement 5"))
					.doFinally(x -> ps.close())
					.filter(s -> s.getCompletedCredits() < 180 && s.getCompletedCredits() >= 120)
					.sort((s1, s2) -> s1.getCompletedCredits() == s2.getCompletedCredits() ? 0
							: s1.getCompletedCredits() < s2.getCompletedCredits() ? 1 : -1)
					.subscribe(s -> {
						System.out.println(
								"Name: " + s.getName() + " Average Grade: " + s.getAverageGrade()
										+ " Completed Credits: "
										+ s.getCompletedCredits() + " BirthDate: " + s.getBirthDate());
						ps.println(
								"Name: " + s.getName() + " Average Grade: " + s.getAverageGrade()
										+ " Completed Credits: "
										+ s.getCompletedCredits() + " BirthDate: " + s.getBirthDate());
					});

		} catch (Exception e) {
			System.out.println("An exception has occurred on requirement 5");
		}
	}

	public void requirement_6() {
		try {
			PrintStream ps = new PrintStream(FILE_6);

			Flux<Student> studentFlux = webClient.get().uri("/students").retrieve().bodyToFlux(Student.class)
					.retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1)));

			Mono<Long> totalStudentMono = studentFlux.count();

			Mono<Float> averageMono = Mono.zip(
					studentFlux
							.map(s -> s.getAverageGrade())
							.reduce(0f, (acc, val) -> acc + val),
					totalStudentMono,
					(s, t) -> s / t).doOnNext(mean -> {
						System.out.println("Average: " + mean);
						ps.println("Average: " + mean);
					});

			averageMono.flatMapMany(avg -> studentFlux
					.map(s -> Tuples.of(avg, s)))
					.doOnError(e -> System.out.println("Error on requirement 6"))
					.doFinally(x -> ps.close())
					.subscribe(t -> {
						System.out.println("Std: " + (t.getT2().getAverageGrade()- t.getT1()  ));
						ps.println("Std: " + (t.getT2().getAverageGrade()- t.getT1()  ));
					});

		} catch (Exception e) {
			System.out.println("An exception has occurred on requirement 6");
		}
	}

	public void requirement_7() {
		try {
			PrintStream ps = new PrintStream(FILE_7);
			Flux<Student> studentGraduateFlux = webClient.get().uri("/students").retrieve().bodyToFlux(Student.class)
					.retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1)))
					.filter(s -> s.getCompletedCredits() == 180);
			Mono<Long> totalGraduatedStudentMono = studentGraduateFlux.count();

			Mono<Float> averageMono = Mono.zip(
					studentGraduateFlux
							.map(s -> s.getAverageGrade())
							.reduce(0f, (acc, val) -> acc + val),
					totalGraduatedStudentMono,
					(s, t) -> s / t).doOnNext(avg -> {
						ps.println("Average: " + avg);
						System.out.println("Average: " + avg);
					});

			averageMono
					.doOnError(e -> System.out.println("Error on requirement 7"))
					.doFinally(x -> ps.close())
					.flatMapMany(avg -> studentGraduateFlux.map(s -> Tuples.of(avg, s)))
					.subscribe(t -> {
						System.out.println("Std: " + ( t.getT2().getAverageGrade() - t.getT1() ));
						ps.println("Std: " + ( t.getT2().getAverageGrade() - t.getT1()));
					});

		} catch (Exception e) {
			System.out.println("An exception has occurred on requirement 7");
		}
	}

	public void requirement_8() {
		try {
			PrintStream ps = new PrintStream(FILE_8);
			Flux<Student> studentFlux = webClient.get().uri("/students").retrieve().bodyToFlux(Student.class)
					.retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1)));

			studentFlux
					.doOnError(e -> System.out.println("Error on requirement 8"))
					.doFinally(x -> ps.close())
					.reduce((s1, s2) -> s1.getBirthDate().isBefore(s2.getBirthDate()) ? s1 : s2)
					.subscribe(s -> {
						ps.println("Eldest name: " + s.getName());
						System.out.println("Eldest name: " + s.getName());
					});
		} catch (Exception e) {
			System.out.println("An exception has occurred on requirement 8");
		}
	}

	public void requirement_9() {
		try {
			PrintStream ps = new PrintStream(FILE_9);
			Flux<Student> studentFlux = webClient.get().uri("/students").retrieve().bodyToFlux(Student.class)
					.retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1)));
			Flux<Professor> professorFlux = webClient.get().uri("/professors").retrieve().bodyToFlux(Professor.class)
					.retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1)));

			Flux.zip(professorFlux.count(), studentFlux.count(), (tp, ts) -> (float) tp / ts)
					.doOnError(e -> System.out.println("Error on requirement 9"))
					.doFinally(x -> ps.close())
					.subscribe(mean -> {
						ps.println("Average professors per student: " + mean);
						System.out.println("Average professors per student: " + mean);
					});

		} catch (Exception e) {
			System.out.println("An exception has occurred on requirement 9");
		}
	}

	public void requirement_10() {
		try {
			PrintStream ps = new PrintStream(FILE_10);

			Flux<Professor> professorFlux = webClient.get().uri("/professors").retrieve().bodyToFlux(Professor.class)
					.retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1)));

			Flux<StudentProfessor> relations = webClient.get().uri("/relations").retrieve()
					.bodyToFlux(StudentProfessor.class).retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1)));

			professorFlux
					.zipWith(professorFlux.flatMap(p -> relations.filter(r -> r.getProfessorId() == p.getId()).count()))
					.doOnError(e -> System.out.println("Error on requirement 10"))
					.doFinally(x -> ps.close())
					.sort((a, b) -> b.getT2().compareTo(a.getT2()))
					.subscribe(t -> {
						ps.println("Professor: " + t.getT1().getName() + "\tNumber of students: " + t.getT2());
						System.out.println("Professor: " + t.getT1().getName() + "\tNumber of students: " + t.getT2());
					});

		} catch (Exception e) {
			System.out.println("An exception has occurred on requirement 10");
		}
	}

	public void requirement_11() {
		try {
			PrintStream ps = new PrintStream(FILE_11);
			Flux<Student> studentFlux = webClient.get().uri("/students").retrieve().bodyToFlux(Student.class)
					.retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1))).publishOn(Schedulers.boundedElastic());
			Flux<Professor> professorFlux = webClient.get().uri("/professors").retrieve().bodyToFlux(Professor.class)
					.retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1))).publishOn(Schedulers.boundedElastic());
			Flux<StudentProfessor> relations = webClient.get().uri("/relations").retrieve()
					.bodyToFlux(StudentProfessor.class).retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1)))
					.publishOn(Schedulers.boundedElastic());

			studentFlux
					.doOnError(e -> System.out.println("Error on requirement 11"))
					.doFinally(x -> ps.close())
					.subscribe(s -> {
						System.out.println("Name: " + s.getName() + " Average Grade: " + s.getAverageGrade()
								+ " Completed Credits: " + s.getCompletedCredits() + " BirthDate: " + s.getBirthDate());
						ps.println("Name: " + s.getName() + " Average Grade: " + s.getAverageGrade()
								+ " Completed Credits: " + s.getCompletedCredits() + " BirthDate: " + s.getBirthDate());
						relations.filter(r -> r.getStudentId() == s.getId())
								.flatMap(r -> professorFlux.filter(p -> p.getId() == r.getProfessorId())
										.map(p -> p.getName()))
								.doOnNext(p -> {
									System.out.println("\t" + p);
									ps.println("\t" + p);
								})
								.blockLast();
					});

		} catch (Exception e) {
			System.out.println(e);
			System.out.println("An exception has occurred on requirement 11");
		}
	}
}
