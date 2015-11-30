package cmput301exchange.exchange;

/**
 * Created by Kaleb on 10/8/2015.
 */

public class PhotoActivityTest {

    public PhotoActivityTest() {}


}




/*

  // testing case US06.01.01
  // goal: attach photo to items

  public void testAttachPhoto(Photo photo) {

	  ItemList item = new ItemList;
	  item.add(photo); // in build, call testImageSize() here
	  assertSame(item.getPhoto(), photo);

  }


/**********************************************************************


  // testing case US06.02.01
  // goal: View attached photo

  public void testViewPhoto(Photo photo) {

	  ItemList item = new ItemList;
	  item.add(photo);

	  assertNotNull(getResources().getDrawable(R.drawable.item_photo));
  }





  // testing case US06.03.01
  // goal: Delete photo

  public void testDeletePhoto(Photo photo) {

	  ItemList item = new ItemList;
	  item.delete(photo);
	  assertThat(item.getPhoto(), false);
  }



// testing case US06.04.01
// goal: Image file less than 65536 bytes

  public void testImageSize(Photo photo) {

	  final int MAXSIZE = 65536;
	  ItemList item = new ItemList;
	  item.add(photo);
	  assert(item.imageSize <= MAXSIZE - 1);
	}



// testing case US06.05.01
// goal: enable/disable downloads


  public void testPhotoDownloads() {

	  boolean loaded;
	  ItemList item = new ItemList;
	  item.loadImages = true;       // implies user chose to have thumbnails downlaoded

	  if (item.loadImages == true) {
  		loaded = item.GetImage();
	  	assertTrue(loaded)

	  }
	}
}
*/