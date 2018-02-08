package runtime.law;


import runtime.source.Source;

import java.io.Serializable;

/**
 * A law produce a value for a given timestamp
 *
 * @param <T> type of produced data
 */
public abstract class Law<T extends Serializable> extends Source<T> {

}
