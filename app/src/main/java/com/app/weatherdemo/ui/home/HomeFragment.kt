package com.app.weatherdemo.ui.home


import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.weatherdemo.R
import com.app.weatherdemo.appdata.CityAdapter
import com.app.weatherdemo.apputils.Constants
import com.app.weatherdemo.apputils.CoroutinesHelper
import com.app.weatherdemo.apputils.digitsOnly
import com.app.weatherdemo.databinding.FragmentHomeBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import com.google.android.gms.maps.model.LatLng
import android.view.MenuInflater





@AndroidEntryPoint
class HomeFragment : Fragment(), OnMapReadyCallback,
    GoogleMap.OnMarkerDragListener, View.OnClickListener {

    lateinit var bindingFragmentHomeBinding: FragmentHomeBinding
   lateinit var address: Address
    private var geocode: String? = null
    private  val viewModel: HomeViewModel by lazy {
       ViewModelProvider(this).get(HomeViewModel::class.java)
   }
     lateinit var cityAdapter: CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        bindingFragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        //bindingFragmentHomeBinding. //setting screen
        return bindingFragmentHomeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        fetchData()
        obsereveViewModelItems()
    }

    private fun bindViews(){

        cityAdapter = CityAdapter(CityAdapter.ClickListener{
                weatherEntity ->
            val bundle = Bundle()
            bundle.putString(Constants.CITY_LOCATION, weatherEntity!!.cityName!!)
            bundle.putString(Constants.CITY_LAT, ""+weatherEntity.coord?.lat)
            bundle.putString(Constants.CITY_LONG, ""+weatherEntity.coord?.lon)
            if(weatherEntity.deleteBtnClicked){
                viewModel.deleteCityFromDb(weatherEntity.objectToDelete)
                CoroutinesHelper.delayMain(700){
                    cityAdapter.notifyItemChanged(weatherEntity.position)
                }
            }else
            findNavController().navigate(R.id.action_homeFragment_to_weatherDetails, bundle) })
        bindingFragmentHomeBinding.cityRecylerview.adapter = cityAdapter
        val mapFragment = getChildFragmentManager().findFragmentById(R.id.google_map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
        bindingFragmentHomeBinding.btnSave.setOnClickListener(this)
    }
    private fun fetchData(){
        viewModel.getAllCitiesFromDb()
    }
    private fun obsereveViewModelItems(){
        with(viewModel){
            cityList.observe(viewLifecycleOwner){ cities ->
                if(cities==null){
                    //add cities
                    addDefaultCityOnStart()
                }else{
                    if (cities.size<1){
                        addDefaultCityOnStart()
                    }else{
                        cityAdapter.submitList(cities)
                    }
                }
            }
         }

    }

    /*****
     * Adding some default cities to db as per requirement
     */
    private fun addDefaultCityOnStart(){
        val cityList = Constants.getArrayListOfRespWeather()
        for (city in cityList){
            viewModel.saveCityDeatilsInDb(city)
        }
        updateViewOnFirstLaunch()
    }
    private fun updateViewOnFirstLaunch(){
        CoroutinesHelper.delayMain(3500){
            fetchData()
        }
    }
    override fun onMapReady(googleMap: GoogleMap) {
        val delhi = LatLng(28.644800
            , 77.216721)
        googleMap.addMarker(
            MarkerOptions()
                .position(delhi)
                .title("Your marker is in Delhi. Select a location")
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(delhi))
        googleMap.setOnMapClickListener {
            googleMap.clear()
            googleMap.addMarker(
                MarkerOptions()
                    .position(it)
                    .draggable(true)
            )
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it, 7.0f), 900, null)
            googleMap.setOnMarkerDragListener(this)
            val gc = extractGeocode(it.toString()).split(",")
            val lat = gc[0].toDouble()
            val long = gc[1].toDouble()
            val geocoder = Geocoder(context, Locale.getDefault())
            address = geocoder.getFromLocation(lat, long,2)[0]
            bindingFragmentHomeBinding.tvGeocodeValue.text = ""+ address.subAdminArea +" " + address.adminArea
        }
    }

    override fun onMarkerDragStart(marker: Marker) {
    }

    override fun onMarkerDrag(marker: Marker) {
        setGeocode(marker)
    }

    override fun onMarkerDragEnd(marker: Marker) {
        setGeocode(marker)
    }

    private fun setGeocode(marker: Marker) {
        val position = marker.position
        geocode = formatGeocode(position.latitude, position.longitude)
        val geocoder = Geocoder( context,Locale.getDefault())
        val address = geocoder.getFromLocation(position.latitude, position.longitude,2)[0]
        bindingFragmentHomeBinding.tvGeocodeValue.text =""+ address.subAdminArea +" " + address.adminArea
    }

    private fun extractGeocode(latLong: String): String {
        var result = ""
        val pattern = Regex("""\(([^]]+)\)""")
        val match = pattern.find(latLong)
        if (match != null) {
            result = match.groupValues[1]
        }
        return result
    }

    private fun formatGeocode(lat: Double, long: Double): String {
        val newLat = String.format("%.10f", lat)
        val newLong = String.format("%.10f", long)
        return "$newLat,$newLong"
    }

    override fun onClick(v: View?) {
       when(v?.id){
           R.id.btnSave ->{
               try {
                   bindingFragmentHomeBinding.tvGeocodeValue.text =
                       "" + address.subAdminArea + " " + address.adminArea
                   val lat = ""+address.latitude.toString().substring(0, 9)
                   val long = ""+address.longitude.toString().substring(0,9)
                   saveCityWeatherDetailsInDb(
                       lat.toDouble(),
                       long.toDouble(),
                       address.subAdminArea
                   )
               }catch (e:Exception){
                   e.printStackTrace()
                   Toast.makeText(context,"This location could not be saved. Please pick another", Toast.LENGTH_SHORT).show()
               }
           }
       }
    }

    /*****
     * Adding  city to db
     */
    private fun saveCityWeatherDetailsInDb(lat: Double,long: Double, city:String){
        var id = Integer.parseInt(String().digitsOnly(""+lat).substring(0,8))
        val cityWeather = Constants.getWeatherResponse(lat,long,city, id)
        viewModel.saveCityDeatilsInDb(cityWeather)
        fetchData()
        Toast.makeText(context, "Address Saved", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_settings) {
            findNavController().navigate(R.id.action_homeFragment_to_webViewFragment)
        }
        return true
    }

}