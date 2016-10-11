

public class MainActivity extends Activity 
	implements Consulta.ConsultaListener
{
	private static final String ID_JSON = "";
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		new ConsultaListener(this, ID_JSON);
	}

	@Override
	public void onConsultaConcluida(String estado)
	{
		findViewById(R.id.tvResultado).setText(estado);
	}
}