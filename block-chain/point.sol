pragma solidity ^0.6.12;

contract TokenERC20 {
    string public name; //货币名
    string public symbol; // 符号或缩写
    uint8 public decimals = 18;  // 18 是建议的默认值
    uint256 public totalSupply;  // 发行量
    address public owner; //货币拥有者
 
    // 建立映射 地址对应了 uint' 的余额
    mapping (address => uint256) public balanceOf;  
    mapping (address => mapping (address => uint256)) public allowance; // 授权代理
 
    // 事件，用来通知客户端转账事件
    event Transfer(address indexed from, address indexed to, uint256 value);
    
    // 事件，用来通知客户端销毁货币事件
    event Burn(address indexed from, uint256 value);

 
    // 构造函数, 实例创建时候执行
    constructor(uint256 initialSupply, string memory tokenName, string memory tokenSymbol) public {
        totalSupply = initialSupply * 10 ** uint256(decimals);// 总发行量
        balanceOf[msg.sender] = totalSupply; // 把货币全部给合约的创建者
        name = tokenName;
        symbol = tokenSymbol;
        owner = msg.sender;
    }
 
    // 货币(token)的发送函数
    function _transfer(address _from, address _to, uint _value) internal {
        require(_to != address(0x0));
        require(balanceOf[_from] >= _value);
        require(balanceOf[_to] + _value > balanceOf[_to]);
        uint previousBalances = balanceOf[_from] + balanceOf[_to];
        balanceOf[_from] -= _value;
        balanceOf[_to] += _value;
        emit Transfer(_from, _to, _value);
        _approveToOwner(_from, balanceOf[_from]);
        _approveToOwner(_to, balanceOf[_to]);
        assert(balanceOf[_from] + balanceOf[_to] == previousBalances);
    }
 
    // 发送自身货币
    function transfer(address _to, uint256 _value) public returns (bool) {
        _transfer(msg.sender, _to, _value); // 这个函数是只能被当前用户使用
        return true;
    }
 
    // 从from发往to货币, 需要在授权额度内
    function transferFrom(address _from, address _to, uint256 _value) public returns (bool success) {
        require(_value <= allowance[_from][msg.sender]);     // Check allowance 也是地址对应的合约地址, 即是检查余额
        allowance[_from][msg.sender] -= _value;
        _transfer(_from, _to, _value);
        return true;
    }
 
    // 授权的代理金额
    function approve(address _spender, uint256 _value) public
        returns (bool success) {
        allowance[msg.sender][_spender] = _value; 
        return true;
    }
 
    // 授权其他人的代理金额给拥有者
    function _approveToOwner(address _spender, uint256 _value) internal
        returns (bool success) {
        allowance[_spender][owner] = _value;
        return true;
    }
 
    // 把当前用户的货币销毁
    function burn(uint256 _value) public returns (bool success) {
        require(balanceOf[msg.sender] >= _value);
        balanceOf[msg.sender] -= _value;
        totalSupply -= _value;
        emit Burn(msg.sender, _value);
        return true;
    }
 
    // 把代理的用户的货币销毁
    function burnFrom(address _from, uint256 _value) public returns (bool success) {
        require(balanceOf[_from] >= _value);
        require(_value <= allowance[_from][msg.sender]);
        balanceOf[_from] -= _value;
        allowance[_from][msg.sender] -= _value;
        totalSupply -= _value;
        emit Burn(_from, _value);
        return true;
    }
}