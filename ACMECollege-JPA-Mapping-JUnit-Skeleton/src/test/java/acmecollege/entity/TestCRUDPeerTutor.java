package acmecollege.entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import common.JUnitBase;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class TestCRUDPeerTutor extends JUnitBase {

	private EntityManager em;
	private EntityTransaction et;	
	private static PeerTutor peerTutor;

	
	@BeforeAll
	static void setupAllInit() {
			peerTutor = new PeerTutor();
			peerTutor.setFirstName("");
		}

	@BeforeEach
	void setup() {
		em = getEntityManager();
		et = em.getTransaction();
	}

	@AfterEach
	void tearDown() {
		em.close();
	}
	
	@AfterAll
	static void tearDownAll( ) {
		   
	    deleteAllData(); 
	}

	@Test
	@Order(1)
	void test01_Empty() {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		// Create query for long as we need the number of found rows
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		// Select count(ptr) from PeerTutorRegistration ptr
		Root<PeerTutor> root = query.from(PeerTutor.class);
		query.select(builder.count(root));
		// Create query and set the parameter
		TypedQuery<Long> tq = em.createQuery(query);
		// Get the result as row count
		long result = tq.getSingleResult();

		assertThat(result, is(comparesEqualTo(0L)));

	}

	@Test
	@Order(2)
	void test02_Create() {
		et.begin();
		peerTutor = new PeerTutor();
		peerTutor.setFirstName("Miok");
		peerTutor.setLastName("Kim");
		peerTutor.setProgram("Korean");
		
		em.persist(peerTutor);
		et.commit();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		// Create query for long as we need the number of found rows
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		// Select count(ptr) from PeerTutorRegistration ptr where ptr.id = :id
		Root<PeerTutor> root = query.from(PeerTutor.class);
		query.select(builder.count(root));
		query.where(builder.equal(root.get(PeerTutor_.id), builder.parameter(Integer.class, "id")));
		// Create query and set the parameter
		TypedQuery<Long> tq = em.createQuery(query);
		tq.setParameter("id", peerTutor.getId());
		// Get the result as row count
		long result = tq.getSingleResult();

		// There should only be one row in the DB
		assertThat(result, is(greaterThanOrEqualTo(1L)));
//		assertEquals(result, 1);
	}

	@Test
	@Order(3)
	void test03_CreateInvalid() {
		et.begin();
		PeerTutor peerTutor2 = new PeerTutor();
//		peerTutorRegistration2.setPeerTutor(peerTutor);
////		peerTutorRegistration2.setCourse(course);
//		peerTutorRegistration2.setStudent(student);
//		peerTutorRegistration2.setNumericGrade(85);
//		peerTutorRegistration2.setLetterGrade("A");
		// We expect a failure because course is part of the composite key
		assertThrows(PersistenceException.class, () -> em.persist(peerTutor2));
		et.commit();
	}

	@Test
	@Order(4)
	void test04_Read() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		// Create query for PeerTutorRegistration
		CriteriaQuery<PeerTutor> query = builder.createQuery(PeerTutor.class);
		// Select ptr from PeerTutorRegistration ptr
		Root<PeerTutor> root = query.from(PeerTutor.class);
		query.select(root);
		// Create query and set the parameter
		TypedQuery<PeerTutor> tq = em.createQuery(query);
		// Get the result as row count
		List<PeerTutor> peerTutors = tq.getResultList();

		assertThat(peerTutors, contains(equalTo(peerTutor)));
	}

	@Test
	@Order(5)
	void test05_ReadDependencies() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		// Create query for PeerTutorRegistration
		CriteriaQuery<PeerTutor> query = builder.createQuery(PeerTutor.class);
		// Select ptr from PeerTutorRegistration ptr
		Root<PeerTutor> root = query.from(PeerTutor.class);
		query.select(root);
		query.where(builder.equal(root.get(PeerTutor_.id), builder.parameter(Integer.class, "id")));
		// Create query and set the parameter
		TypedQuery<PeerTutor> tq = em.createQuery(query);
		tq.setParameter("id", peerTutor.getId());
		// Get the result as row count
		PeerTutor returnedPeerTutor = tq.getSingleResult();

		assertThat(returnedPeerTutor, equalTo(peerTutor));
		assertThat(returnedPeerTutor.getFirstName(), equalTo(peerTutor.getFirstName()));
	    assertThat(returnedPeerTutor.getLastName(), equalTo(peerTutor.getLastName()));
	}
	
	@Test
	@Order(6)
	void test06_Update() {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		// Create query for PeerTutorRegistration
		CriteriaQuery<PeerTutor> query = builder.createQuery(PeerTutor.class);
		// Select ptr from PeerTutorRegistration ptr
		Root<PeerTutor> root = query.from(PeerTutor.class);
		query.select(root);
		query.where(builder.equal(root.get(PeerTutor_.id), builder.parameter(Integer.class, "id")));
		// Create query and set the parameter
		TypedQuery<PeerTutor> tq = em.createQuery(query);
		tq.setParameter("id", peerTutor.getId());
		// Get the result as row count
		PeerTutor returnedPeerTutor = tq.getSingleResult();

		String newFirstName = "Updated First Name";
	    String newLastName = "Updated Last Name";

		et.begin();
		returnedPeerTutor.setFirstName(newFirstName);
		returnedPeerTutor.setLastName(newLastName);
		em.merge(returnedPeerTutor);
		et.commit();

		returnedPeerTutor = tq.getSingleResult();

		assertThat(returnedPeerTutor.getFirstName(), equalTo(newFirstName));
		assertThat(returnedPeerTutor.getLastName(), equalTo(newLastName));
	}
}

