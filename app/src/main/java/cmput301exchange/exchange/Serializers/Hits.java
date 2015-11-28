package cmput301exchange.exchange.Serializers;

import java.util.List;

/**
 * Created by Charles on 11/27/2015.
 */
public class Hits<T> {
    private int total;
    private float max_score;

    public List<SearchHit<T>> getHits() {
        return hits;
    }

    public void setHits(List<SearchHit<T>> hits) {
        this.hits = hits;
    }

    private List<SearchHit<T>> hits;

    public Hits(){}

    public int getTotal(){
        return total;
    }
    public void setTotal(int total){
        this.total = total;
    }
    public List<SearchHit<T>> getSearchResults(){
        return hits;
    }
    public void setResults(List<SearchHit<T>> hits){
        this.hits = hits;
    }

    @Override
    public String toString(){
        return getSearchResults().toString();
    }

}
