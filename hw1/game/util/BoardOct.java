package hw1.game.util;

import hw1.game.board.Board;
import hw1.game.board.Pos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** <b>IMPLEMENTARE I METODI SECONDO LE SPECIFICHE DATE NEI JAVADOC. Non modificare
 * le intestazioni dei metodi.</b>
 * <br>
 * Gli oggetti BoardOct implementano l'interfaccia {@link Board} per rappresentare
 * board generali con sistema di coordinate {@link Board.System#OCTAGONAL}
 * modificabili.
 * @param <P>  tipo del modello dei pezzi */
public class BoardOct<P> implements Board<P> {
    /** Crea una BoardOct con le dimensioni date (può quindi essere rettangolare).
     * Le posizioni della board sono tutte quelle comprese nel rettangolo dato e le
     * adiacenze sono tutte e otto, eccetto per le posizioni di bordo.
     * @param width  larghezza board
     * @param height  altezza board
     * @throws IllegalArgumentException se width <= 0 o height <= 0 */
    public BoardOct(int width, int height) {
        if(width <= 0 || height <= 0) 
        	throw new IllegalArgumentException("Illegal board dimension");
        this.width = width;
        this.height = height;
        for(int w = 0; w < width; w ++)
        	for(int h = 0; h < height; h++){
        		this.pos.add(new Pos(w, h));
        		this.posPiece.put(new Pos(w, h), null);
        	}
        
    }

    /** Crea una BoardOct con le dimensioni date (può quindi essere rettangolare)
     * escludendo le posizioni in exc. Le adiacenze sono tutte e otto, eccetto per
     * le posizioni di bordo o adiacenti a posizioni escluse. Questo costruttore
     * permette di creare board per giochi come ad es.
     * <a href="https://en.wikipedia.org/wiki/Camelot_(board_game)">Camelot</a>
     * @param width  larghezza board
     * @param height  altezza board
     * @param exc  posizioni escluse dalla board
     * @throws NullPointerException se exc è null
     * @throws IllegalArgumentException se width <= 0 o height <= 0 */
    public BoardOct(int width, int height, Collection<? extends Pos> exc) {
    	if(width <= 0 || height <= 0) throw new IllegalArgumentException("Illegal board dimension");
        if(exc==null) throw new NullPointerException("Position absent is null");
    	this.width = width;
        this.height = height;
        for(int w = 0; w < width; w ++)
        	for(int h = 0; h < height; h++)
        		if(!exc.contains(new Pos(w, h))){
        			this.pos.add(new Pos(w, h));
        			this.posPiece.put(new Pos(w, h), null);
        		}
      
    }

    @Override
    public System system() {
        return System.OCTAGONAL;
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public Pos adjacent(Pos p, Dir d) {
        if(p == null || d == null) throw new NullPointerException("One or more parameters are null"); 
        Pos posApp = null;
        int b  = p.b;
        int t = p.t;
        switch(d){
        	case UP: 
        		if (t < height) posApp = (isPos(new Pos(b,t+1)) ? new Pos(b,t+1):null); break;
        	case DOWN:
        		if (t > 0) posApp = (isPos(new Pos(b,t-1)) ? new Pos(b,t-1):null); break;
        	case LEFT:
        		if (b > 0) posApp = (isPos(new Pos(b-1, t)) ? new Pos(b-1,t):null); break;
        	case RIGHT:
        		if (b < width) posApp = (isPos(new Pos(b+1, t))?new Pos(b+1,t):null); break;
        	case UP_L:
        		if (t < height && b > 0) posApp = (isPos(new Pos(b+1, t))?new Pos(b+1,t):null); break;
        	case UP_R:
        		if (t < height && b < width) posApp = (isPos(new Pos(b+1, t+1))?new Pos(b+1,t+1):null); break;
        	case DOWN_R:
        		if (b < width && t > 0) posApp = (isPos(new Pos(b+1, t-1))?new Pos(b+1,t-1):null); break;
        	case DOWN_L:
        		if (b > 0 && t > 0) posApp = (isPos(new Pos(b-1, t-1))?new Pos(b-1,t-1):null); break;
        }
        return posApp;
    }

    @Override
    public List<Pos> positions() {
        return Collections.unmodifiableList(pos);
    }

    @Override
    public P get(Pos p) {
        if(p == null) throw new NullPointerException("The position parameter is null");
        if(posPiece.get(p)==null) return null;
        return posPiece.get(p);
    }

    @Override
    public boolean isModifiable() { return true; }

    @Override
    public P put(P pm, Pos p) {
    	if(!isModifiable()) throw new UnsupportedOperationException("The board is unmodifiable");
        if(p == null|| pm == null) throw new NullPointerException("One or more parameters are null");
    	if(!isPos(p)) throw new IllegalArgumentException("the position doesnt exist");
    	P pre = get(p);
    	posPiece.put(p, pm);
    	return pre;
    }

    @Override
    public P remove(Pos p) {
    	if(!isModifiable()) throw new UnsupportedOperationException("The board is unmodifiable");
        if(p == null) throw new NullPointerException("The parameter is null");
    	if(!isPos(p)) throw new IllegalArgumentException("the position doesnt exist");
    	P pre = get(p);
    	posPiece.put(p, null);
    	return pre;
    }
    
    private Map<Pos,P> posPiece = new HashMap<Pos,P>();
    
    private int width, height;
    
    private List<Pos> pos = new ArrayList<Pos>();
}
