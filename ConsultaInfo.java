//Add pakage this context
// gt.empresa.desarrollo;

import android.os.AsynTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Consulta extends AsynTask<Void, Void, String>
{
	private String url_api ;
	private static final int TIMEOUT = 10000;
	private static final int TIME_CONNETION = 15000;
	private ConsultaListener listener;
	private String id_json = "";
	public static final String REQUEST_METHOD_GET = "GET";
	public static final String REQUEST_METHOD_POST = "POST";

	public Consulta(ConsultaListener listener, String id_json)
	{
		this.listener = listener;
		this.id_json = id_json;
	}

	@Override
	protected String doInBackground(Void... params)
	{
		try
		{
			String resultado = onConsultaServidor();
			return onInterpreteResultado(resultado);
		}catch (IOException e) {
			e.printStackTrace();
		}catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private String onInterpreteResultado(String resultado)
		 throws JSONException
	{
		JSONObject json = new JSONObject(resultado);
		JSONArray json_array = json.getJSONArray(id_json);
		JSONObject resultado_json = json_array.getJSONObjet(0);
		int id = resultado_json.getInt(id); //change id
		String descripcion = resultado_json.getString(body); // change body
		String result = id + descripcion;
		return result;
	}

	private String onConsultaServidor() 
		throws IOException
	{
		InputStream is = null;
		try
		{
			URL url = new URL(url_api);
			HttpURLConnection con = (HttpURLConnection)
				 url.openConnection();
			con.setReadTimeout(TIMEOUT);
			con.setConnectionTimeout(TIME_CONNETION);
			con.setRequestMethod(REQUEST_METHOD_POST);
			con.setDoInput(true);
			con.connect();
			con.getResponseCode();

			is = con.getInputStream(is);

			Reader r = null;
			r = new InputStreamReader(is);
			char[] bufer = new char[2048];
			r.read(bufer);
			return new String(bufer);
		}
		finally
		{
			if(is != null){
				is.close();
			}
		}
	}

	@Override
	protected void onPostExecute(String resultado)
	{
		listener.onConsultaConcluida(resultado);
	}

	public interface ConsultaListener
	{
		void onConsultaConcluida(String estado);
	}
	
}


