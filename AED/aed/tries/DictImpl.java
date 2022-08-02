 package aed.tries;

import java.util.Arrays;
import java.util.Iterator;

import es.upm.aedlib.Pair;
import es.upm.aedlib.Position;
import es.upm.aedlib.tree.GeneralTree;
import es.upm.aedlib.tree.LinkedGeneralTree;
import es.upm.aedlib.positionlist.PositionList;
import es.upm.aedlib.positionlist.NodePositionList;

public class DictImpl implements Dictionary {
	// A boolean because we need to know if a word ends in a node or not
	GeneralTree<Pair<Character, Boolean>> tree;

	public DictImpl() {
		tree = new LinkedGeneralTree<>();
		tree.addRoot(new Pair<Character, Boolean>(null, false));
	}

	// AUX METHOD
	private Position<Pair<Character, Boolean>> addChildAlphabetically(Pair<Character, Boolean> pair,
			Position<Pair<Character, Boolean>> pos) {
		Iterator<Position<Pair<Character, Boolean>>> it = tree.children(pos).iterator();
		if (!it.hasNext()) {
			return tree.addChildFirst(pos, pair);
		}
		while (it.hasNext()) {
			Position<Pair<Character, Boolean>> x = it.next();
			if (x.element().getLeft().compareTo(pair.getLeft()) > 0) {
				return tree.insertSiblingBefore(x, pair);
			}
		}
		return tree.addChildLast(pos, pair);

	}

	private Position<Pair<Character, Boolean>> findPos(String prefix) {
		Iterator<Position<Pair<Character, Boolean>>> it = tree.children(tree.root()).iterator();
		int i = 0;
		if (prefix.length() == 0) {
			return tree.root();
		}
		while (it.hasNext()) {
			Position<Pair<Character, Boolean>> x = it.next();
			if (x.element().getLeft().equals(prefix.charAt(i))) {
				if (prefix.length() == i + 1) {
					return x;
				} else {
					it = tree.children(x).iterator();
					i++;
				}
			}
		}
		return null;
	}

	public void add(String word) {
		if (word == null || word.length() == 0) {
			throw new IllegalArgumentException();
		}
		if (!isIncluded(word)) {
			addRec(word, tree.root(), 0);
		}
	}

	private void addRec(String word, Position<Pair<Character, Boolean>> nodo, int i) {

		Iterator<Position<Pair<Character, Boolean>>> it = tree.children(nodo).iterator();
		boolean listo = false;
		while (it.hasNext()) {

			Position<Pair<Character, Boolean>> x = it.next();

			if (x.element().getLeft().equals(word.charAt(i))) {

				if (word.length() == i + 1) {
					x.element().setRight(true);
					listo = true;
					break;
				} else {
					addRec(word, x, i + 1);
					listo = true;
				}
			}
		}
		if (!it.hasNext() && !listo) {
			if (word.length() == i + 1) {
				Pair<Character, Boolean> par = new Pair<>(word.charAt(i), true);
				addChildAlphabetically(par, nodo);
			} else {
				Pair<Character, Boolean> par = new Pair<>(word.charAt(i), false);
				addRec(word, addChildAlphabetically(par, nodo), i + 1);
			}
		}
	}

	public void delete(String word) {

		if (word == null || word.length() == 0) {
			throw new IllegalArgumentException();
		}
		Position<Pair<Character, Boolean>> pos = findPos(word);

		if (pos != null && pos.element().getRight()) {
			pos.element().setRight(false);
		}

	}

	public boolean isIncluded(String word) {
		if (tree.isEmpty()) {
			return false;
		}
		if (word == null || word.length() == 0) {
			throw new IllegalArgumentException();
		}
		Position<Pair<Character, Boolean>> a = findPos(word);
		if (a == null) {
			return false;
		}
		return a.element().getRight();
	}



	public PositionList<String> wordsBeginningWithPrefix(String prefix) {
		PositionList<String> result = new NodePositionList<>();
		Position<Pair<Character, Boolean>> a = findPos(prefix);
		String Preadd = "";
		if (a == null) {
			return result;
		}
		if (a != tree.root() && tree.parent(a) != tree.root()) {
			Position<Pair<Character, Boolean>> Pre = a;
			while (tree.parent(Pre) != tree.root()) {
				Pre = tree.parent(Pre);
				Preadd = Pre.element().getLeft() + Preadd;
			}

		}
		return wordsBeginningWithPrefix(a, result, Preadd);

	}

	private PositionList<String> wordsBeginningWithPrefix(Position<Pair<Character, Boolean>> a,
			PositionList<String> result, String add) {
		if (a.element().getLeft() != null) {
			add += a.element().getLeft();
		}
		if (a.element().getRight()) {
			result.addLast(add);
		}
		for (Position<Pair<Character, Boolean>> w : tree.children(a)) {
			wordsBeginningWithPrefix(w, result, add);
		}
		return result;

	}
}
