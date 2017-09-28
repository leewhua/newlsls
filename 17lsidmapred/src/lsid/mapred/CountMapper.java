package lsid.mapred;

import java.io.IOException;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

public class CountMapper extends TableMapper<Text, IntWritable>  {
	
	private final IntWritable ONE = new IntWritable(1);

   	public void map(ImmutableBytesWritable row, Result value, Context context) throws IOException, InterruptedException {
   		try{
	   		String eid = Bytes.toString(row.get()).split("_")[1];
	   		if (eid.equals("t1")) {
	   			Text t = new Text();
	   			t.set(eid);
		   		context.write(t, ONE);
	   		}
   		}catch(Exception ex){
   			throw new IOException(ex);
   		}
   		
   		
   	}
}