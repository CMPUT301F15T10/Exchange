package cmput301exchange.exchange.Serializers;

/**
 * Created by Charles on 11/24/2015.
 * This class serves as a mediary between ElasticSearch and the Model.
 * It stores the result for further parsing.
 *
 * Adapted from Joshua2ua Android Elastic Search:
 * https://github.com/joshua2ua/AndroidElasticSearch/blob/master/app/src/main/java/ca/ualberta/ssrg/movies/es/data/SearchHit.java
 */
public class SearchHit<type> {
    private String _index;
    private String _type;
    private String _id;
    private String _version;
    private boolean found;
    private type _source;

    public SearchHit(){
    }

    public String getIndex() {
        return _index;
    }

    public void setIndex(String _index) {
        this._index = _index;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_version() {
        return _version;
    }

    public void set_version(String _version) {
        this._version = _version;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public type get_source() {
        return _source;
    }

    public void set_source(type _source) {
        this._source = _source;
    }

    @Override
    public String toString(){
        return "ElasticSearch Result [_index=" + _index + ",_type=" +_type +", _id=" + _id +", _version=" + _version +", found =" + found + ", _source :" + _source +"]";

    }
}
