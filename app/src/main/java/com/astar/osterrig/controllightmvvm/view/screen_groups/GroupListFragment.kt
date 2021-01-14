package com.astar.osterrig.controllightmvvm.view.screen_groups

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.astar.osterrig.controllightmvvm.R
import com.astar.osterrig.controllightmvvm.databinding.FragmentGroupsBinding
import com.astar.osterrig.controllightmvvm.model.data.AppState
import com.astar.osterrig.controllightmvvm.model.data.DeviceModel
import com.astar.osterrig.controllightmvvm.utils.setBackgroundTint
import com.astar.osterrig.controllightmvvm.view.adapters.GroupDeviceAdapter
import com.astar.osterrig.controllightmvvm.view.base.BaseFragment
import com.astar.osterrig.controllightmvvm.view.dialogs.GroupSabersDialog
import org.koin.android.viewmodel.ext.android.viewModel

internal class GroupListFragment : BaseFragment<GroupListViewModel>() {

    private lateinit var mBinding: FragmentGroupsBinding
    override val mModel: GroupListViewModel by viewModel()
    private lateinit var adapter: GroupDeviceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentGroupsBinding.inflate(layoutInflater, container, false)
        initView()
        renderData()
        mModel.getGroupsData()
        return mBinding.root
    }

    private fun initView() {
        adapter = GroupDeviceAdapter()
        mBinding.btnAddGroup.setOnClickListener { mModel.getDevicesData() }
        mBinding.recyclerDevices.adapter = adapter
        mBinding.recyclerDevices.setHasFixedSize(true)
        mBinding.recyclerDevices.addItemDecoration(
            DividerItemDecoration(
                context,
                RecyclerView.VERTICAL
            )
        )
        with(mBinding.topNavPanel) {
            btnOne.text = getString(R.string.title_tab_sabers)
            btnTwo.text = getString(R.string.title_tab_groups)
            btnOne.setBackgroundColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.button_active_color,
                    null
                )
            )
            btnOne.setOnClickListener { navigateToDeviceList() }
            btnTwo.setBackgroundTint(R.color.button_active_color)
        }
    }

    private fun renderData() {
        mModel.subscribe().observe(viewLifecycleOwner, { appState ->
            when (appState) {
                is AppState.Success -> {
                    val data = appState.data as List<Pair<String, List<DeviceModel>>>
                    adapter.addData(data)
                    hideOrShowNoDeviceText(data.size)
                }
                is AppState.SuccessDevices -> {
                    val data = appState.data as List<DeviceModel>
                    openAddGroupDialog(ArrayList(data))
                }
                is AppState.Error -> {
                    val data = appState.error
                    showToastMessage("Error " + data.message)
                }
            }
        })
    }

    private fun hideOrShowNoDeviceText(size: Int) {
        mBinding.tvNoGroups.visibility = if (size > 0) View.INVISIBLE else View.VISIBLE
    }

    private fun openAddGroupDialog(deviceList: ArrayList<DeviceModel>) {
        val dialog = GroupSabersDialog.newInstance(deviceList)
        dialog.addCallback(object : GroupSabersDialog.Callback {
            override fun createGroup(nameGroup: String, data: List<DeviceModel>) {
                mModel.createGroup(nameGroup, data)
                Handler().postDelayed({ mModel.getGroupsData() }, 100)
            }
        })
        dialog.show(childFragmentManager, "add_group_dialog")
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            GroupListFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}