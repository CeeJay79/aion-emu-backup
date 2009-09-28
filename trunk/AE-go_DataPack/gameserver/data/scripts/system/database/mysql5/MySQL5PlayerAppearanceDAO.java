/*
 * This file is part of aion-emu <aion-emu.com>.
 *
 * aion-emu is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aion-emu is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with aion-emu.  If not, see <http://www.gnu.org/licenses/>.
 */

package mysql5;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.IUStH;
import com.aionemu.commons.database.ParamReadStH;
import com.aionemu.gameserver.dao.PlayerAppearanceDAO;
import com.aionemu.gameserver.model.gameobjects.player.PlayerAppearance;
import com.aionemu.gameserver.utils.collections.cachemap.CacheMap;
import com.aionemu.gameserver.utils.collections.cachemap.CacheMapFactory;

/**
 * Class that is responsible for loading/storing {@link com.aionemu.gameserver.model.gameobjects.player.PlayerAppearance} in mysql5
 * 
 * @author SoulKeeper
 */
public class MySQL5PlayerAppearanceDAO extends PlayerAppearanceDAO
{
	private static final Logger log = Logger.getLogger(PlayerAppearanceDAO.class);
	
	private CacheMap<Integer, PlayerAppearance> cache = CacheMapFactory.createCacheMap("Appereance", "appereance");
	
	private ReentrantLock lock = new ReentrantLock();
	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlayerAppearance load(final int playerId)
	{
		PlayerAppearance app = cache.get(playerId);
		if(app != null)
		{
			log.debug("[DAO: MySQL5PlayerAppearanceDAO] obtained appereance from cache "+playerId);
			return app;
		}
		
		final PlayerAppearance pa = new PlayerAppearance();
		boolean success = DB.select("SELECT * FROM player_appearance WHERE player_id = ?", new ParamReadStH() {

			@Override
			public void handleRead(ResultSet resultSet) throws SQLException
			{
				log.debug("[DAO: MySQL5PlayerAppearanceDAO] loaded appereance for player: "+playerId);
				resultSet.next();
				pa.setFace(resultSet.getInt("face"));
				pa.setHair(resultSet.getInt("hair"));
				pa.setDeco(resultSet.getInt("deco"));
				pa.setTattoo(resultSet.getInt("tattoo"));
				pa.setSkinRGB(resultSet.getInt("skin_rgb"));
				pa.setHairRGB(resultSet.getInt("hair_rgb"));
				pa.setLipRGB(resultSet.getInt("lip_rgb"));
				pa.setFaceShape(resultSet.getInt("face_shape"));
				pa.setForehead(resultSet.getInt("forehead"));
				pa.setEyeHeight(resultSet.getInt("eye_height"));
				pa.setEyeSpace(resultSet.getInt("eye_space"));
				pa.setEyeWidth(resultSet.getInt("eye_width"));
				pa.setEyeSize(resultSet.getInt("eye_size"));
				pa.setEyeShape(resultSet.getInt("eye_shape"));
				pa.setEyeAngle(resultSet.getInt("eye_angle"));
				pa.setBrowHeight(resultSet.getInt("brow_height"));
				pa.setBrowAngle(resultSet.getInt("brow_angle"));
				pa.setBrowShape(resultSet.getInt("brow_shape"));
				pa.setNose(resultSet.getInt("nose"));
				pa.setNoseBridge(resultSet.getInt("nose_bridge"));
				pa.setNoseWidth(resultSet.getInt("nose_width"));
				pa.setNoseTip(resultSet.getInt("nose_tip"));
				pa.setCheek(resultSet.getInt("cheek"));
				pa.setLipHeight(resultSet.getInt("lip_height"));
				pa.setMouthSize(resultSet.getInt("mouth_size"));
				pa.setLipSize(resultSet.getInt("lip_size"));
				pa.setSmile(resultSet.getInt("smile"));
				pa.setLipShape(resultSet.getInt("lip_shape"));
				pa.setJawHeigh(resultSet.getInt("jaw_height"));
				pa.setChinJut(resultSet.getInt("chin_jut"));
				pa.setEarShape(resultSet.getInt("ear_shape"));
				pa.setHeadSize(resultSet.getInt("head_size"));
				pa.setNeck(resultSet.getInt("neck"));
				pa.setNeckLength(resultSet.getInt("neck_length"));
				pa.setShoulders(resultSet.getInt("shoulders"));
				pa.setTorso(resultSet.getInt("torso"));
				pa.setChest(resultSet.getInt("chest"));
				pa.setWaist(resultSet.getInt("waist"));
				pa.setHips(resultSet.getInt("hips"));
				pa.setArmThickness(resultSet.getInt("arm_thickness"));
				pa.setHandSize(resultSet.getInt("hand_size"));
				pa.setLegThicnkess(resultSet.getInt("leg_thickness"));
				pa.setFootSize(resultSet.getInt("foot_size"));
				pa.setFacialRate(resultSet.getInt("facial_rate"));
				pa.setVoice(resultSet.getInt("voice"));
				pa.setHeight(resultSet.getFloat("height"));
			}

			@Override
			public void setParams(PreparedStatement preparedStatement) throws SQLException
			{
				preparedStatement.setInt(1, playerId);
			}
		});

		if(success)
		{
			try{
				lock.lock();
				PlayerAppearance cached = cache.get(playerId);
				if (cached != null)
					return cached;
				cache.put(playerId, pa);
				return pa;
			}
			finally
			{
				lock.unlock();
			}
		}
		else
			return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean store(final int id, final PlayerAppearance pa)
	{
		cache.put(id, pa);
		
		return DB.insertUpdate("REPLACE INTO player_appearance ("
			+ "player_id, face, hair, deco, tattoo, skin_rgb, hair_rgb, lip_rgb, face_shape,"
			+ "forehead, eye_height, eye_space, eye_width, eye_size, eye_shape, eye_angle,"
			+ "brow_height, brow_angle, brow_shape, nose, nose_bridge, nose_width, nose_tip, "
			+ "cheek, lip_height, mouth_size, lip_size, smile, lip_shape, jaw_height, chin_jut, ear_shape,"
			+ "head_size, neck, neck_length, shoulders, torso, chest, waist, hips, arm_thickness, hand_size,"
			+ "leg_thickness, foot_size, facial_rate, voice, height)" + " VALUES "
			+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?" + ")", new IUStH() {
			@Override
			public void handleInsertUpdate(PreparedStatement ps) throws SQLException
			{
				log.debug("[DAO: MySQL5PlayerAppearanceDAO] storing appereance "+id);
				ps.setInt(1, id);
				ps.setInt(2, pa.getFace());
				ps.setInt(3, pa.getHair());
				ps.setInt(4, pa.getDeco());
				ps.setInt(5, pa.getTattoo());
				ps.setInt(6, pa.getSkinRGB());
				ps.setInt(7, pa.getHairRGB());
				ps.setInt(8, pa.getLipRGB());
				ps.setInt(9, pa.getFaceShape());
				ps.setInt(10, pa.getForehead());
				ps.setInt(11, pa.getEyeHeight());
				ps.setInt(12, pa.getEyeSpace());
				ps.setInt(13, pa.getEyeWidth());
				ps.setInt(14, pa.getEyeSize());
				ps.setInt(15, pa.getEyeShape());
				ps.setInt(16, pa.getEyeAngle());
				ps.setInt(17, pa.getBrowHeight());
				ps.setInt(18, pa.getBrowAngle());
				ps.setInt(19, pa.getBrowShape());
				ps.setInt(20, pa.getNose());
				ps.setInt(21, pa.getNoseBridge());
				ps.setInt(22, pa.getNoseWidth());
				ps.setInt(23, pa.getNoseTip());
				ps.setInt(24, pa.getCheek());
				ps.setInt(25, pa.getLipHeight());
				ps.setInt(26, pa.getMouthSize());
				ps.setInt(27, pa.getLipSize());
				ps.setInt(28, pa.getSmile());
				ps.setInt(29, pa.getLipShape());
				ps.setInt(30, pa.getJawHeigh());
				ps.setInt(31, pa.getChinJut());
				ps.setInt(32, pa.getEarShape());
				ps.setInt(33, pa.getHeadSize());
				ps.setInt(34, pa.getNeck());
				ps.setInt(35, pa.getNeckLength());
				ps.setInt(36, pa.getShoulders());
				ps.setInt(37, pa.getTorso());
				ps.setInt(38, pa.getChest());
				ps.setInt(39, pa.getWaist());
				ps.setInt(40, pa.getHips());
				ps.setInt(41, pa.getArmThickness());
				ps.setInt(42, pa.getHandSize());
				ps.setInt(43, pa.getLegThicnkess());
				ps.setInt(44, pa.getFootSize());
				ps.setInt(45, pa.getFacialRate());
				ps.setInt(46, pa.getVoice());
				ps.setFloat(47, pa.getHeight());
				ps.execute();
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean supports(String s, int i, int i1)
	{
		return MySQL5DAOUtils.supports(s, i, i1);
	}
}