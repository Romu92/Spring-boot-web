package it.aulab.springbootweb;

import java.util.List;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import it.aulab.springbootweb.model.Fornitore;
import it.aulab.springbootweb.model.Prodotto;
import it.aulab.springbootweb.model.Variante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SpringBootWebApplicationTests {
	@Autowired
	private EntityManager entityManager;
	// gestisce la persistenza (modifica ->rimuovere aggiungere elementi al database) del database



	@Test
	void persistObjectProdotto() {
		Prodotto p= new Prodotto();

		p.setNome("Maglia Nike");
		p.setDescrizione("Maglia in cotone");
		p.setPrezzo(30F);

		entityManager.persist(p);
		TypedQuery<Prodotto> q =entityManager.createQuery("SELECT p FROM Prodotto p", Prodotto.class);

		List<Prodotto> all= q.getResultList();

		Assertions.assertThat(all).hasSize(5);

	}
	
	@Test
	void checkPrezzoNetto(){
		Prodotto p= new Prodotto();

		p.setNome("Maglia Nike");
		p.setDescrizione("Maglia in cotone");
		p.setPrezzo(30F);
		p.setPrezzoNetto(20F);

		entityManager.persist(p);
		TypedQuery<Prodotto> q =entityManager.createQuery("SELECT p FROM Prodotto p where id=1", Prodotto.class);

		Prodotto queryp= q.getSingleResult();
		Assertions.assertThat(queryp).extracting("prezzoNetto").isEqualTo(80F);
	}
	

	@Test
	void checkManytoOneRelation(){
		TypedQuery<Prodotto> q1 =entityManager.createQuery("SELECT p FROM Prodotto p where p.id=1", Prodotto.class);

		Prodotto p= q1.getSingleResult();
		
		TypedQuery<Variante> q2 =entityManager.createQuery("SELECT v FROM Variante v where v.id=1", Variante.class);
		Variante v = q2.getSingleResult();
		Assertions.assertThat(v).extracting("prodotto").isEqualTo(p);
	}
	@Test
	void checkOneToManyRelation(){
		TypedQuery<Prodotto> q1 =entityManager.createQuery("SELECT p FROM Prodotto p where p.id=1", Prodotto.class);

		Prodotto p= q1.getSingleResult();
		Assertions.assertThat(p).extracting("varianti").asList().hasSize(4);
	}
	@Test
	void checkProdottiFromFornitori() {
		TypedQuery<Fornitore> q = entityManager.createQuery("SELECT f FROM Fornitore f", Fornitore.class);

		List<Fornitore> all = q.getResultList();

		Assertions.assertThat(all.get(0)).extracting("prodotti").asList().hasSize(1);
		Assertions.assertThat(all.get(1)).extracting("prodotti").asList().hasSize(4);
		Assertions.assertThat(all.get(2)).extracting("prodotti").asList().hasSize(2);
	}

	@Test
	void checkFornitoriFromProdotti() {
		// String sql = "SELECT p FROM Prodotto p WHERE id = ";

	
		TypedQuery<Prodotto> q1 = entityManager.createQuery("SELECT p FROM Prodotto p WHERE id = 1", Prodotto.class);
		Prodotto p1 = q1.getSingleResult();

		
		Assertions.assertThat(p1).extracting("fornitori").asList().hasSize(2);

		TypedQuery<Prodotto> q2 = entityManager.createQuery("SELECT p FROM Prodotto p WHERE id = 4", Prodotto.class);
		Prodotto p4 = q2.getSingleResult();

		Assertions.assertThat(p4).extracting("fornitori").asList().hasSize(1);
	}

	@Test
	// per inserire una nuova relazione
	void checkRelationInsert() {
		TypedQuery<Prodotto> q1 = entityManager.createQuery("SELECT p FROM Prodotto p WHERE id = 1", Prodotto.class);
		Prodotto p1 = q1.getSingleResult();

	
		Variante newVariant1 = new Variante();
		newVariant1.setAttributo("taglia");
		newVariant1.setValore("xl");
		newVariant1.setProdotto(p1);

		entityManager.persist(newVariant1);

		Variante newVariant2 = new Variante();
		newVariant2.setAttributo("taglia");
		newVariant2.setValore("xs");
		newVariant2.setProdotto(p1);

		entityManager.persist(newVariant2);

		// queryAll per recuperare tutti i prodotti
		TypedQuery<Prodotto> queryAll = entityManager.createQuery("SELECT p FROM Prodotto p", Prodotto.class);
		List<Prodotto> prodotti = queryAll.getResultList();
		// per recuperare tutta la lista di prodotti

		Assertions.assertThat(prodotti.get(0)).extracting("varianti").asList().hasSize(6);

	}

	@Test
	// aggiungiamo un relazione in questo caso al fornitore 1 creando un nuovo prodotto e mettendolo in relazione col fornitore 1
	void checkDeleteRelation() {
		TypedQuery<Fornitore> q1 = entityManager.createQuery("SELECT f FROM Fornitore f WHERE id = 1", Fornitore.class);
		Fornitore f1 = q1.getSingleResult();

		Prodotto p = new Prodotto();
		p.setNome("x");
		p.setDescrizione("x");
		p.setPrezzo(1F);
		p.setPrezzoNetto(0.8F);

		entityManager.persist(p);

		f1.getProdotti().add(p);

		entityManager.persist(f1);

		TypedQuery<Fornitore> queryAll = entityManager.createQuery("SELECT f FROM Fornitore f", Fornitore.class);
		List<Fornitore> fornitori = queryAll.getResultList();

		Assertions.assertThat(fornitori.get(0)).extracting("prodotti").asList().hasSize(2);

		// entityManager.remove(p);
		// la remove fallisce ma va comunque avanti

		// TypedQuery<Fornitore> queryAll2 = entityManager.createQuery("SELECT f FROM Fornitore f", Fornitore.class);
		// List<Fornitore> fornitori2 = queryAll2.getResultList();

		// Assertions.assertThat(fornitori2.get(0)).extracting("prodotti").asList().hasSize(1);

		entityManager.remove(f1);

		TypedQuery<Prodotto> queryProdotto = entityManager.createQuery("SELECT p FROM Prodotto p WHERE id = " + p.getId(), Prodotto.class);
		Prodotto prodotto = queryProdotto.getSingleResult();

		Assertions.assertThat(prodotto).extracting("fornitori").asList().hasSize(0);

	}

}



