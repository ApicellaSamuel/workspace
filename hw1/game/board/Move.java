package hw1.game.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** <b>IMPLEMENTARE I METODI SECONDO LE SPECIFICHE DATE NEI JAVADOC. Non modificare
 * le intestazioni dei metodi nè i campi pubblici.</b>
 * <br>
 * Un oggetto Move rappresenta una mossa di un giocatore nel suo turno di gioco.
 * Gli oggetti Move sono immutabili. Le mosse possono essere di vari tipi
 * {@link Move.Kind}, il tipo più importante è {@link Move.Kind#ACTION} che
 * rappresenta una mossa che cambia la disposizione di uno o più pezzi sulla board
 * del gioco. Una mossa di tipo {@link Move.Kind#ACTION} è composta da una sequenza
 * di una o più azioni, cioè oggetti di tipo {@link Action}, ad es. la mossa di una
 * pedina nella Dama che salta e mangia un'altra pedina è composta da un'azione di
 * tipo {@link Action.Kind#JUMP} seguita da un'azione di tipo
 * {@link Action.Kind#REMOVE}.
 * @param <P>  tipo del modello dei pezzi */
public class Move<P> {
    /** Tipi di una mossa */
    public enum Kind {
        /** Effettua una o più azioni ({@link Action}) */
        ACTION,
        /** Passa il turno di gioco */
        PASS,
        /** Abbandona il gioco, cioè si arrende */
        RESIGN
    }

    /** Tipo della mossa, non è mai null */
    public final Kind kind;
    /** Sequenza di azioni della mossa, non è null solamente se il tipo della mossa
     * è {@link Kind#ACTION}, la lista è immodificabile e ha lunghezza >= 1 */
    public final List<Action<P>> actions;

    /** Crea una mossa che non è di tipo {@link Kind#ACTION}.
     * @param k  tipo della mossa
     * @throws NullPointerException se k è null
     * @throws IllegalArgumentException se k è {@link Kind#ACTION} */
    public Move(Kind k) {
        if(k == null) throw new NullPointerException("The move type is null");
        if(k == Kind.ACTION) throw new IllegalArgumentException("The move type is action");
        this.kind=k;
        this.actions=new ArrayList<Action<P>>();
    }

    /** Crea una mossa di tipo {@link Kind#ACTION}.
     * @param aa  la sequenza di azioni della mossa
     * @throws NullPointerException se una delle azioni è null
     * @throws IllegalArgumentException se non è data almeno un'azione */
    @SafeVarargs
    public Move(Action<P>...aa) {
    	if(aa.length < 1) throw new IllegalArgumentException("At least one action");
        for(Action<P> a : aa) if(a == null) throw new NullPointerException("One or more action is null");
        this.kind = Kind.ACTION;
        this.actions = new ArrayList<Action<P>>();
        for(Action<P> a : aa) this.actions.add(a);
    }

    /** Crea una mossa di tipo {@link Kind#ACTION}. La lista aa è solamente letta e
     * non è mantenuta nell'oggetto creato.
     * @param aa  la sequenza di azioni della mossa
     * @throws NullPointerException se aa è null o una delle azioni è null
     * @throws IllegalArgumentException se non è data almeno un'azione */
    public Move(List<Action<P>> aa) {
    	if(aa == null) throw new NullPointerException("The parameter is null");
    	if(aa.size() < 1) throw new IllegalArgumentException("At least one action");
        for(Action<P> a : aa) if(a == null) throw new NullPointerException("One or more action is null");
        this.kind = Kind.ACTION;
        this.actions = new ArrayList<Action<P>>();
        for(Action<P> a : aa) this.actions.add(a);
    }

    /** Ritorna true se e solo se x è un oggetto di tipo {@link Move} ed ha gli
     * stessi valori dei campi {@link Move#kind} e {@link Move#actions}.
     * @param x  un oggetto (o null)
     * @return true se x è uguale a questa mossa */
    @Override
    public boolean equals(Object x) {
    	if(x != null && x.getClass() == getClass())
    		
    		if (x.hashCode()==hashCode()) return true;
        
    	return false;
    }

    /** Ridefinito coerentemente con la ridefinizione di
     * {@link PieceModel#equals(Object)}.
     * @return hash code di questa mossa */
    @Override
    public int hashCode() {
        return Objects.hash(kind, actions);
    }
}
